package processor;

import ast.Entity;
import ast.ID;
import base.ElException;
import base.Module;
import base.SimpleMap;
import java.util.HashMap;
import java.util.LinkedList;
import vm.*;

public class Processor extends ProBase {
  static EReader reader;
  static final HashMap<String, VMCommand> commands = new HashMap<>();
  static final HashMap<String, ProCommand> proCommands = new HashMap<>();
  static ID defaultID = ID.get("default");
  
  static {
    commands.put("I64Equate", new I64VarEquate(0));
    commands.put("I64Add", new I64Add());
    commands.put("I64Push", new I64Push(0));
    commands.put("I64StackPush", new I64VarPush(0));
    commands.put("I64ToString", new I64ToString());
    commands.put("StringAdd", new StringAdd());
    commands.put("StringPush", new StringPush(""));
    commands.put("StringStackPush", new StringVarPush(0));
    commands.put("print", new VMPrint());
    
    proCommands.put("getFromScope", new ProGetFromScope());
    proCommands.put("resolveAll", new ProResolveAll());
    proCommands.put("convert", new ProConvert());
  }
  
  static class ProcessorObject extends SimpleMap<ID, LinkedList<ProCommand>> {}
  
  final HashMap<ID, ProcessorObject> methods = new HashMap<>();
  
  ProcessorObject getObject(String name) {
    return getObject(ID.get(name));
  }
  
  ProcessorObject getObject(ID id) {
    ProcessorObject function = methods.get(id);
    if(function == null) {
      function = new ProcessorObject();
      methods.put(id, function);
    }
    return function;
  }
  
  LinkedList<ProCommand> getMethod(ProcessorObject function
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
        String[] part = line.substring(0, line.length() - 1).split("\\.");
        ProcessorObject function = getObject(part[0]);
        LinkedList<ProCommand> method = getMethod(function
            , part.length > 1 ? ID.get(part[1]) : defaultID);
        if(!readCode(method).equals("}"))
          throw new ElException("Case outside switch.");
      }
    } catch (ElException ex) {
      error("Error in processor code", currentFileName + " (" + currentLineNum + ")\n"
      + ex.message);
    }
    return this;
  }
  
  String readCode(LinkedList<ProCommand> method) throws ElException {
    String line;
    while(true) {
      if((line = reader.readLine()) == null)
        throw new ElException("Unexpected end of file");
      if(line.equals("}")) return line;
      if(line.startsWith("case")) {
        return line;
      } else if(line.startsWith("switch")) {
        readSwitch(method, line);
      } else {
        line = expectEnd(line, ";");
        if(line.contains(".")) {
          String[] part = trimmedSplit(line, '.', '(', ')');
          method.add(new ProCall(part[0], part[1], part[2]));
        } else {
          ProCommand proCommand = proCommands.get(line);
          if(proCommand != null) {
            method.add(proCommand.create());
          } else {
            VMCommand command = commands.get(line);
            if(command == null)
              throw new ElException("Command " + line + " is not found.");
            method.add(new ProAppendCommand(command));
          }
        }
      }
    }
  }
  
  void readSwitch(LinkedList<ProCommand> method, String line)
      throws ElException {
    ProSwitch sw = new ProSwitch(betweenBrackets(line, '(', ')'));
    method.add(sw);
    if((line = reader.readLine()) == null)
      throw new ElException("Unexpected end of file");
    while(true) {
      if(line.equals("}")) break;
      if(!line.startsWith("case"))
        throw new ElException("Syntax error in switch.");
      line = readCode(sw.addCase(expectEnd(line, ":").substring(4)));
    }
  }
  
  
  public void call(Entity object, ID method) throws ElException {
    current = object;
    ProcessorObject function = methods.get(object.getObject());
    if(function == null) throw new ElException("No code for " + object);
    LinkedList<ProCommand> code = function.get(method);
    if(code == null) throw new ElException("No code for " + object + "."
        + method);
    executeCode(code);
  }
  
  public void call(Entity object, ID method, Entity parent) throws ElException {
    Entity oldParent = Processor.parent;
    Processor.parent = parent;
    call(object, method);
    Processor.parent = oldParent;
  }
  
  public void call(Entity object) throws ElException {
    call(object, defaultID, null);
  }

  void executeCode(LinkedList<ProCommand> code) throws ElException {
    for(ProCommand command : code) {
      currentLineNum = command.lineNum;
      command.execute();
    }
  }
  
  public void process(Module module) {
    try {
      currentProcessor = this;
      module.process();
    } catch (ElException ex) {
      error("Error while processing", currentFileName + " (" + currentLineNum + ")\n"
      + ex.message);
    }
  }
}
