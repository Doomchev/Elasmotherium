package processor;

import vm.i64.I64IteratorNext;
import vm.list.ListToIterator;
import ast.Block;
import vm.*;
import vm.iterator.*;
import vm.object.*;
import vm.i64.*;
import vm.string.*;
import ast.Entity;
import ast.FunctionCall;
import ast.ID;
import ast.Link;
import base.ElException;
import base.Module;
import base.LinkedMap;
import java.util.HashMap;
import java.util.LinkedList;

public class Processor extends ProBase {
  static final HashMap<String, VMCommand> commands = new HashMap<>();

  private static final HashMap<String, ProCommand> proCommands = new HashMap<>();
  private static final ID defaultID = ID.get("default");
  private static EReader reader;
  
  private static void addCommand(VMCommand command) {
    commands.put(command.getClassName(), command);
  }
  
  static {
    addCommand(new I64Push(0));
    addCommand(new StringPush(""));
    
    addCommand(new I64VarPush(0));
    addCommand(new StringVarPush(0));
    addCommand(new ObjectVarPush(0));
    
    addCommand(new I64VarEquate(0));
    addCommand(new StringVarEquate(0));
    addCommand(new ObjectVarEquate(0));
    
    addCommand(new I64FieldPush(0));
    addCommand(new StringFieldPush(0));
    
    addCommand(new I64ThisFieldPush(0));
    addCommand(new StringThisFieldPush(0));
    
    addCommand(new I64FieldEquate(0));
    addCommand(new StringFieldEquate(0));
    
    addCommand(new I64ThisFieldEquate(0));
    addCommand(new StringThisFieldEquate(0));
    
    addCommand(new I64Add());
    addCommand(new I64Subtract());
    addCommand(new I64Multiply());
    addCommand(new I64Mod());
    
    addCommand(new I64FieldIncrement(0));
    addCommand(new I64ThisFieldIncrement(0));
    addCommand(new I64VarIncrement(0));
    
    addCommand(new StringAdd());
    
    addCommand(new I64IsEqual());
    
    addCommand(new I64IsLess());
    addCommand(new I64IsLessOrEqual());
    addCommand(new I64IsMore());
    
    addCommand(new I64Return());
    addCommand(new StringReturn());
    
    addCommand(new ListToIterator());
    addCommand(new IteratorHasNext());
    addCommand(new I64IteratorNext());
    
    addCommand(new GoTo());
    addCommand(new IfFalseGoTo());
    
    proCommands.put("getField", GetField.instance);
    proCommands.put("setObject", SetObject.instance);
    proCommands.put("call", Call.instance);
    proCommands.put("convert", Convert.instance);
    proCommands.put("stop", Stop.instance);
    proCommands.put("process", Process.instance);
  }
  
  private static class ProcessorObject
      extends LinkedMap<ID, LinkedList<ProCommand>> {}
  
  private final HashMap<ID, ProcessorObject> methods = new HashMap<>();
  
  private ProcessorObject getObject(String name) {
    return getObject(ID.get(name));
  }
  
  private ProcessorObject getObject(ID id) {
    ProcessorObject function = methods.get(id);
    if(function == null) {
      function = new ProcessorObject();
      methods.put(id, function);
    }
    return function;
  }
  
  private LinkedList<ProCommand> getMethod(ProcessorObject function
      , ID id) {
    LinkedList<ProCommand> list = function.get(id);
    if(list == null) {
      list = new LinkedList<>();
      function.put(id, list);
    }
    return list;
  }
  
  public Processor load(String fileName) {
    currentFileName = fileName;
    try {
      reader = new EReader(fileName);
      String line;
      while((line = reader.readLine()) != null) {
        line = expectEnd(line, "{");
        String[] part = line.split("\\.");
        ProcessorObject function = getObject(part[0]);
        ID methodID = part.length > 1 ? ID.get(part[1]) : defaultID;
        LinkedList<ProCommand> method = getMethod(function, methodID);
        while(true) {
          if((line = reader.readLine()) == null)
            throw new ElException("Unexpected end of file");
          if(line.equals("}")) break;
          if(line.startsWith("#")) {
            ID labelID = ID.get(expectEnd(line, ":").substring(1));
            method.add(new BlockLabelSet(labelID));
            method.addFirst(new BlockLabelInitialize(labelID));
          } else {
            line = expectEnd(line, ";");
            String param = line.contains("(") ? betweenBrackets(line) : "";
            line = stringUntil(line, '(');
            if(line.startsWith("[")) {
              part = trimmedSplit(line, '[', ']');
              method.add(new TypeCommand(part[1], part[2], param));
            } else {
              part = trimmedSplit(line, '.', '(');
              if(line.contains(".")) {
                method.add(new ProCall(part[0], part[1], param));
              } else {
                ProCommand proCommand = proCommands.get(line);
                if(proCommand != null) {
                  method.add(proCommand.create(param));
                } else {
                  VMCommand command = commands.get(line);
                  if(command == null)
                    throw new ElException("Command " + line + " is not found.");
                  method.add(AppendCommand.create(command, param));
                }
              }
            }
          }
        }
      }
    } catch (ElException ex) {
      error("Error in processor code"
          , currentFileName + " (" + currentLineNum + ")\n"
      + ex.message);
    }
    return this;
  }
  
  public void callBlock(Block block) throws ElException {
    block.parentBlock = currentBlock;
    currentBlock = block;
    call(block);
    currentBlock = block.parentBlock;
  }
  
  public void call(Entity object, ID method) throws ElException {
    Entity oldCurrent = current;
    current = object;
    ID objectId = object.getObject();
    if(objectId == Link.id) {
      current = getFromScope(object.getName());
      if(current == null)
        throw new ElException(object.getName() + " is not found.");
      objectId = current.getObject();
    }
    ProcessorObject function = methods.get(objectId);
    LinkedList<ProCommand> code
        = function == null ? null : function.get(method);
    if(code == null) {
      if(method == FunctionCall.resolve)
        object.resolveAll();
      else
        throw new ElException("No code for " + objectId + "." + method);
    } else {
      for(ProCommand command: code) {
        currentLineNum = command.lineNum;
        command.execute();
      }
    }
    current = oldCurrent;
  }
  
  public void call(Entity object, ID method, Entity param)
      throws ElException {
    Entity oldParam = Processor.param;
    Processor.param = param;
    call(object, method);
    Processor.param = oldParam;
  }
  
  public void call(Entity object) throws ElException {
    call(object, defaultID, null);
  }

  public void process(Module module) {
    try {
      currentProcessor = this;
      module.process();
    } catch (ElException ex) {
      error("Error while processing", currentFileName + " (" + currentLineNum
          + ")\n" + ex.message);
    }
  }
}
