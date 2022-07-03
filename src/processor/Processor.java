package processor;

import ast.Block;
import ast.Entity;
import ast.ID;
import ast.function.FunctionCall;
import base.LineReader;
import base.LinkedMap;
import base.Module;
import exception.ElException;
import exception.ElException.MethodException;
import exception.EntityException;
import processor.block.BlockLabelInitialize;
import processor.block.BlockLabelSet;
import processor.parameter.SetParameter;
import vm.GoTo;
import vm.IfFalseGoTo;
import vm.VMCommand;
import vm.bool.And;
import vm.bool.Or;
import vm.call.I64Return;
import vm.call.ObjectReturn;
import vm.call.ReturnVoid;
import vm.call.StringReturn;
import vm.collection.*;
import vm.f64.var.F64VarEquate;
import vm.f64.var.F64VarPush;
import vm.i64.*;
import vm.i64.field.I64FieldEquate;
import vm.i64.field.I64FieldIncrement;
import vm.i64.field.I64FieldPush;
import vm.i64.var.I64VarEquate;
import vm.i64.var.I64VarIncrement;
import vm.i64.var.I64VarPush;
import vm.object.ObjectFieldEquate;
import vm.object.ObjectFieldPush;
import vm.object.ObjectVarEquate;
import vm.object.ObjectVarPush;
import vm.string.StringAdd;
import vm.string.StringPush;
import vm.string.field.StringFieldEquate;
import vm.string.field.StringFieldPush;
import vm.string.var.StringVarEquate;
import vm.string.var.StringVarPush;

import java.util.HashMap;
import java.util.LinkedList;

public class Processor extends ProBase {
  public static final ID callMethod = ID.get("call");
  public static final ID resolveMethod = ID.get("resolve");
  public static final ID getObjectMethod = ID.get("getObject");
  
  static final HashMap<String, VMCommand> commands = new HashMap<>();

  private static final HashMap<String, ProCommand> proCommands
      = new HashMap<>();
  
  private static void addCommand(VMCommand command) {
    commands.put(command.getClassName(), command);
  }
  
  static {
    addCommand(new I64Push(0));
    addCommand(new StringPush(""));
    
    addCommand(new I64VarPush(0));
    addCommand(new F64VarPush(0));
    addCommand(new StringVarPush(0));
    addCommand(new ObjectVarPush(0));
    
    addCommand(new I64VarEquate(0));
    addCommand(new F64VarEquate(0));
    addCommand(new StringVarEquate(0));
    addCommand(new ObjectVarEquate(0));
    
    addCommand(new I64FieldPush(0, 0));
    addCommand(new StringFieldPush(0, 0));
    
    addCommand(new I64FieldEquate(0, 0));
    addCommand(new StringFieldEquate(0, 0));
    addCommand(new ObjectFieldEquate(0, 0));

    addCommand(new I64Negative());
    addCommand(new I64Add());
    addCommand(new I64Subtract());
    addCommand(new I64Multiply());
    addCommand(new I64Divide());
    addCommand(new I64Mod());
    
    addCommand(new I64FieldIncrement(0, 0));
    addCommand(new I64VarIncrement(0));
    
    addCommand(new StringAdd());

    addCommand(new ObjectFieldPush(0, 0));
    
    addCommand(new I64IsEqual());
    addCommand(new I64IsNotEqual());
    addCommand(new I64IsLess());
    addCommand(new I64IsLessOrEqual());
    addCommand(new I64IsMore());

    addCommand(new And());
    addCommand(new Or());

    addCommand(new ReturnVoid());
    addCommand(new I64Return());
    addCommand(new StringReturn());
    addCommand(new ObjectReturn());

    addCommand(new CollectionToIterator());
    addCommand(new IteratorHasNext());
    addCommand(new I64IteratorNext());
    addCommand(new I64GetAtIndex());
    addCommand(new I64SetAtIndex());
    addCommand(new ObjectGetAtIndex());
    addCommand(new ObjectSetAtIndex());

    addCommand(new GoTo());
    addCommand(new IfFalseGoTo());
    
    proCommands.put("getField", GetField.instance);
    proCommands.put("getSubType", GetSubType.instance);
    proCommands.put("setParameter", SetParameter.instance);
    proCommands.put("convert", Convert.instance);
    proCommands.put("stop", Stop.instance);
    proCommands.put("compile", Process.instance);
  }
  
  private static class Method {
    boolean hasLabels = false;
    LinkedList<ProCommand> commands = new LinkedList<>();

    public void execute() throws ElException, EntityException {
      for(ProCommand command: commands) command.execute();
    }
  }
  
  private final HashMap<ID, LinkedMap<ID, Method>>
      functions = new HashMap<>();
  
  private Method getMethod(ID functionName, ID methodName)
      throws ElException {
    if(log2) println("[getting method " + functionName + "." + methodName +"]");
    LinkedMap<ID, Method> function = functions.get(functionName);
    if(function == null)
      throw new MethodException("Processor", "getMethod"
          , "No code for " + functionName);
    Method method = function.get(methodName);
    if(method == null)
      throw new MethodException("Processor", "getMethod"
          , "No code for " + currentParam);
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
    try {
      currentLineReader = new LineReader(fileName);
      String line;
      while((line = currentLineReader.readLine()) != null) {
        line = expectEnd(line, "{");
        String[] part = line.split("\\.");
        LinkedMap<ID, Method> function = newFunction(ID.get(part[0]));
        ID methodID = part.length > 1 ? ID.get(part[1]) : callMethod;
        Method method = newMethod(function, methodID);
        LinkedList<ProCommand> code = method.commands;
        while(true) {
          if((line = currentLineReader.readLine()) == null)
            throw new MethodException("Processor", "load"
                , "Unexpected end of file");
          if(line.equals("}")) break;
          if(line.startsWith("#")) {
            ID labelID = ID.get(expectEnd(line, ":").substring(1));
            code.add(new BlockLabelSet(labelID));
            code.addFirst(new BlockLabelInitialize(labelID));
            method.hasLabels = true;
          } else {
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
                    throw new ElException.NotFound(this, "Command "
                        + line);
                  code.add(AppendCommand.create(command, param));
                }
              }
            }
          }
        }
      }
    } catch (ElException ex) {
      error("processor code", ex.message);
    }
    return this;
  }

  public void compileBlock(Block block, ID type)
      throws ElException, EntityException {
    if(log2) println("[processing block " + block + " with type " + type + "]");
    block.parentBlock = currentBlock;
    currentBlock = block;
    getMethod(type, callMethod).execute();
    currentBlock = block.parentBlock;
  }
  
  public void getObject(Entity entity) throws EntityException, ElException {
    compileCall(entity, entity.getID(), getObjectMethod);
  }
  
  public void resolve(Entity entity, Entity type)
      throws ElException, EntityException {
    compileCall(entity, entity.getID(), resolveMethod, type);
  }
  
  public void resolveCall(FunctionCall call, ID functionName, Entity type)
      throws ElException, EntityException {
    compileCall(call, functionName, resolveMethod, type);
  }
  
  public void compileCall(Entity entity, ID method, Entity param)
      throws ElException, EntityException {
    compileCall(entity, entity.getID(), method, param);
  }
  
  public void compileCall(Entity entity, ID function, ID method, Entity type)
      throws ElException, EntityException {
    if(log2) println("[call" + entity + "." + function + "." + method + " of "
        + type + "]");
    Entity oldParam = Processor.currentParam;
    Processor.currentParam = type;
    compileCall(entity, function, method);
    Processor.currentParam = oldParam;
  }

  public void compileCall(FunctionCall call, ID functionName)
      throws ElException, EntityException {
    compileCall(call, functionName, callMethod);
  }
  
  public void compileCall(Entity object, ID functionName, ID methodName)
      throws ElException, EntityException {
    if(log2) println("[call object " + object + " " + functionName + "."
        + methodName + "]");
    Entity oldCurrent = currentObject;
    currentObject = object.resolve();
    compile(functionName, methodName);
    currentObject = oldCurrent;
  }
  
  public void compile(ID functionName, ID methodName)
      throws ElException, EntityException {
    if(log2) currentLineReader.log("[" + functionName + "."
        + methodName + "]", 0);
    Method method;
    try {
      method = getMethod(functionName, methodName);
    } catch(ElException ex) {
      if(methodName == resolveMethod) {
        currentObject.resolveTo(currentParam.getType());
      } else if(methodName == getObjectMethod) {
        currentParam = currentObject.getObject();
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

  public void compile(Module module) {
    try {
      currentProcessor = this;
      module.process();
    } catch (EntityException ex) {
      //ex.entity.showDebugMessage(ex.message);
      error("Processing error", ex.message);
    } catch (exception.NotFound ex) {
      error("Processing error", ex.message);
    }
  }
}
