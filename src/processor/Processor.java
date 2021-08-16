package processor;

import ast.ClassEntity;
import ast.Entity;
import ast.FunctionCall;
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

  private static void addCommand(VMCommand command) {
    commands.put(command.getName(), command);
  }
  
  static {
    addCommand(new I64Push(0));
    addCommand(new StringPush(""));
    
    addCommand(new I64VarPush(0));
    addCommand(new StringVarPush(0));
    
    addCommand(new I64VarEquate(0));
    
    addCommand(new I64Add());
    addCommand(new I64Subtract());
    addCommand(new I64Multiply());
    
    addCommand(new StringAdd());
    
    addCommand(new I64IsEqual());
    
    addCommand(new I64IsLess());
    addCommand(new I64IsLessOrEqual());
    addCommand(new I64IsMore());
    
    addCommand(new I64Return(0));
    
    addCommand(new GoTo());
    addCommand(new IfFalseGoTo());
    
    proCommands.put("getFromScope", new GetFromScope());
    proCommands.put("resolveAll", new ResolveAll());
    proCommands.put("stop", new Stop());
    proCommands.put("process", new Process(null));
    proCommands.put("return", new ProReturn(null));
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
        ID methodID = part.length > 1 ? ID.get(part[1]) : defaultID;
        LinkedList<ProCommand> method = getMethod(function, methodID);
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
      if(line.startsWith("#")) {
        method.add(new SetLabel(ID.get(expectEnd(line, ":"))));
      } else if(line.startsWith("case")) {
        return line;
      } else if(line.startsWith("switch")) {
        readSwitch(method, line);
      } else {
        line = expectEnd(line, ";");
        if(line.contains(".")) {
          String[] part = trimmedSplit(line, '.', '(', ')');
          method.add(new ProCall(part[0], part[1], part[2]));
        } else {
          String param = "";
          if(line.contains("(")) {
            param = betweenBrackets(line);
            line = stringUntil(line, '(');
          }
          ProCommand proCommand = proCommands.get(line);
          if(proCommand != null) {
            method.add(proCommand.create(param));
          } else {
            VMCommand command = commands.get(line);
            if(command == null)
              throw new ElException("Command " + line + " is not found.");
            method.add(new AppendCommand(command, param));
          }
        }
      }
    }
  }
  
  void readSwitch(LinkedList<ProCommand> method, String line)
      throws ElException {
    Switch sw = new Switch(between(line, '(', ')'));
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
    Entity oldCurrent = current;
    LinkedList<ProLabel> oldLabels = ProLabel.all;
    ProLabel.all = new LinkedList<>();
    while(true) {
      current = object;
      ID objectId = object.getObject();
      ProcessorObject function = methods.get(objectId);
      if(function == null) {
        if(method == FunctionCall.resolve) {
          object.resolveAll();
          break;
        }
        throw new ElException("No code for " + objectId);
      }
      LinkedList<ProCommand> code = function.get(method);
      if(code == null)
        throw new ElException("No code for " + objectId + "." + method);
      executeCode(code);
      break;
    }
    ProLabel.apply();
    ProLabel.all = oldLabels;
    current = oldCurrent;
  }
  
  public void call(Entity object, ID method, ClassEntity targetClass)
      throws ElException {
    ClassEntity oldTargetClass = Processor.targetClass;
    Processor.targetClass = targetClass;
    call(object, method);
    if(targetClass != null) convert(targetClass);
    Processor.targetClass = oldTargetClass;
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
    if(log) printChapter("Processing");
    try {
      currentProcessor = this;
      module.process();
    } catch (ElException ex) {
      error("Error while processing", currentFileName + " (" + currentLineNum
          + ")\n" + ex.message);
    }
  }
}
