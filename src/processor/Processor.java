package processor;

import processor.parameter.SetObject;
import processor.block.BlockLabelInitialize;
import processor.block.BlockLabelSet;
import vm.call.*;
import vm.string.var.*;
import vm.string.field.*;
import vm.i64.var.*;
import vm.i64.field.*;
import ast.Block;
import vm.*;
import vm.object.*;
import vm.i64.*;
import vm.string.*;
import vm.collection.*;
import ast.Entity;
import ast.ID;
import ast.function.FunctionCall;
import static base.Base.currentLineNum;
import base.ElException;
import base.ElException.MethodException;
import base.ElException.NotFound;
import base.LinkedMap;
import base.Module;
import java.util.HashMap;
import java.util.LinkedList;

public class Processor extends ProBase {
  public static final ID callMethod = ID.get("call");
  public static final ID resolveMethod = ID.get("resolve");
  public static final ID getObjectMethod = ID.get("getObject");
  
  static final HashMap<String, VMCommand> commands = new HashMap<>();

  private static final HashMap<String, ProCommand> proCommands = new HashMap<>();
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
    addCommand(new I64Divide());
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
    
    addCommand(new CollectionToIterator());
    addCommand(new IteratorHasNext());
    addCommand(new I64IteratorNext());
    addCommand(new I64GetAtIndex());
    addCommand(new I64SetAtIndex());
    
    addCommand(new GoTo());
    addCommand(new IfFalseGoTo());
    
    proCommands.put("getField", GetField.instance);
    proCommands.put("setObject", SetObject.instance);
    proCommands.put("convert", Convert.instance);
    proCommands.put("stop", Stop.instance);
    proCommands.put("process", Process.instance);
  }
  
  private static class Method {
    boolean hasLabels = false;
    LinkedList<ProCommand> commands = new LinkedList<>();

    public void execute() throws ElException {
      for(ProCommand command: commands) {
        currentLineNum = command.lineNum;
        command.execute();
      }
    }
  }
  
  private final HashMap<ID, LinkedMap<ID, Method>>
      functions = new HashMap<>();
  
  private Method getMethod(ID functionName, ID methodName)
      throws ElException {
    LinkedMap<ID, Method> function = functions.get(functionName);
    if(function == null)
      throw new MethodException("Processor", "getMethod"
          , "No code for " + functionName);
    Method method = function.get(methodName);
    if(method == null)
      throw new MethodException("Processor", "getMethod"
          , "No code for " + object + "." + method);
    return method;
  }
  
  private LinkedMap<ID, Method> newFunction(ID functionName) {
    LinkedMap<ID, Method> function = functions.get(functionName);
    if(function == null) {
      function = new LinkedMap<>();
      functions.put(functionName, function);
    }
    return function;
  }
  
  private Method newMethod(
      LinkedMap<ID, Method> function, ID methodName) {
    Method method = function.get(methodName);
    if(method == null) {
      method = new Method();
      function.put(methodName, method);
    }
    return method;
  }
  
  public Processor load(String fileName) {
    currentFileName = fileName;
    try {
      reader = new EReader(fileName);
      String line;
      while((line = reader.readLine()) != null) {
        line = expectEnd(line, "{");
        String[] part = line.split("\\.");
        LinkedMap<ID, Method> function = newFunction(ID.get(part[0]));
        ID methodID = part.length > 1 ? ID.get(part[1]) : callMethod;
        Method method = newMethod(function, methodID);
        LinkedList<ProCommand> code = method.commands;
        while(true) {
          if((line = reader.readLine()) == null)
            throw new MethodException("Processor", "load"
                , "Unexpected end of file");
          if(line.equals("}")) break;
          if(line.startsWith("#")) {
            ID labelID = ID.get(expectEnd(line, ":").substring(1));
            code.add(new BlockLabelSet(labelID));
            code.addFirst(new BlockLabelInitialize(labelID));
            method.hasLabels = true;
          } else {
            line = expectEnd(line, ";");
            String param = line.contains("(") ? betweenBrackets(line) : "";
            line = stringUntil(line, '(');
            if(line.startsWith("[")) {
              part = trimmedSplit(line, '[', ']');
              code.add(new TypeCommand(part[1], part[2], param));
            } else {
              part = trimmedSplit(line, '.', '(');
              if(line.contains(".")) {
                code.add(new ProCall(part[0], part[1], param));
              } else {
                ProCommand proCommand = proCommands.get(line);
                if(proCommand != null) {
                  code.add(proCommand.create(param));
                } else {
                  VMCommand command = commands.get(line);
                  if(command == null)
                    throw new NotFound(this, "Command " + line);
                  code.add(AppendCommand.create(command, param));
                }
              }
            }
          }
        }
      }
    } catch (ElException ex) {
      error("Error in processor code"
          , currentFileName + " (" + currentLineNum + ")\n" + ex.message);
    }
    return this;
  }

  public void processBlock(Block block, ID type) throws ElException {
    block.parentBlock = currentBlock;
    currentBlock = block;
    getMethod(type, callMethod).execute();
    currentBlock = block.parentBlock;
  }
  
  /*public void resolve(Entity object, ID functionName, FunctionCall call
      , Entity param) throws ElException {
    Entity oldParam = Processor.param;
    Processor.param = param;
    process(object, functionName, resolveMethod, call);
    Processor.param = oldParam;
  }*/
  
  public void getObject(Entity entity) throws ElException {
    call(entity, entity.getID(), getObjectMethod);
  }
  
  public void resolve(Entity entity, Entity parameter)
      throws ElException {
    call(entity, entity.getID(), resolveMethod, parameter);
  }
  
  public void resolveCall(FunctionCall call, ID functionName, Entity parameter)
      throws ElException {
    call(call, functionName, resolveMethod, parameter);
  }
  
  public void call(Entity entity, ID method, Entity param)
      throws ElException {
    call(entity, entity.getID(), method, param);
  }
  
  public void call(Entity entity, ID function, ID method, Entity parameter)
      throws ElException {
    Entity oldParam = Processor.currentParameter;
    Processor.currentParameter = parameter;
    call(entity, function, method);
    Processor.currentParameter = oldParam;
  }

  public void processCall(FunctionCall call, ID functionName)
      throws ElException {
    call(call, functionName, callMethod);
  }
  
  /*public void resolve(Entity object, Entity param)
      throws ElException {
    Entity oldParam = Processor.param;
    Processor.param = param;
    object = object.resolve();
    process(object, object.getID(), resolveMethod);
    Processor.param = oldParam;
  }*/
  
  public void call(Entity object, ID functionName, ID methodName)
      throws ElException {
    Entity oldCurrent = currentObject;
    currentObject = object.resolve();
    process(functionName, methodName);
    currentObject = oldCurrent;
  }
  
  public void process(ID functionName, ID methodName) throws ElException {
    if(log) System.out.println(subIndent.toString() + currentLineNum + ": "
        + functionName + "." + methodName);
    Method method;
    try {
      method = getMethod(functionName, methodName);
    } catch(ElException ex) {
      if(methodName == resolveMethod) {
        currentObject.resolve(currentParameter.getType().getNativeClass());
      } else if(methodName == getObjectMethod) {
        object = currentObject.getObject();
      } else {
        throw ex;
      }
      return;
    }
    if(method.hasLabels) {
      Block block = currentBlock;
      currentBlock = new Block(null);
      method.execute();
      currentBlock.applyLabels();
      currentBlock = block;
    } else {
      method.execute();
    }
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
