package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import ast.ID;
import ast.Variable;
import base.ElException;
import java.util.LinkedList;
import parser.EntityStack;
import processor.Processor;
import vm.VMCommand;
import vm.call.CallFunction;

public class CustomFunction extends Function {
  private boolean isConstructor = false;
  private ClassEntity parentClass = null;
  private Entity returnType = null;
  private final LinkedList<Variable> parameters = new LinkedList<>();
  private Code code = new Code();
  private int startingCommand;
  private int allocation = 0;
  private VMCommand command = null;
  
  // constructors
  
  public CustomFunction(ID name) {
    super(name);
    if(name == null) this.isConstructor = true;
  }
  
  public CustomFunction(VMCommand command, String name, ClassEntity... paramTypes) {
    super(ID.get(name));
    this.command = command;
    for(ClassEntity paramType: paramTypes)
      parameters.add(new Variable(paramType));
  }
  
  public CustomFunction(VMCommand command, ClassEntity returnType, String name
      , ClassEntity... paramTypes) {
    this(command, name, paramTypes);
    this.returnType = returnType;
  }

  public static CustomFunction create(ID id) {
    return allocateFunction(new CustomFunction(id));
  }
  
  // parameters

  public boolean isMethod() {
    return parentClass != null;
  }

  public int getCallAllocation() {
    return parameters.size() - 1;
  }

  public int getCallDeallocation() {
    return parameters.size() + (isMethod() ? 1 : 0) - (isConstructor ? 1 : 0);
  }

  public int getStartingCommand() {
    return startingCommand;
  }

  public void setReturnType(Entity returnType) {
    this.returnType = returnType;
  }

  @Override
  public int getParametersQuantity() {
    return parameters.size();
  }
  
  // allocation

  public void setAllocation() {
    allocation = Math.max(allocation, currentAllocation);
  }

  public void printAllocation(String fileName) {
    code.print(new StringBuilder(), fileName + ":" + allocation + " ");
  }
  
  // child objects

  public int addParameter(Variable variable) {
    int index = allocation;
    allocation++;
    parameters.add(variable);
    return index;
  } 
  
  public CustomFunction getFunction(ID id, int parametersQuantity)
      throws ElException {
    return code.getFunction(id, parametersQuantity);
  }
  
  public void setCommand(VMCommand command) {
    this.command = command;
  }
  
  // processor fields

  public int getAllocation() {
    return allocation;
  }
  
  @Override
  public ID getID() throws ElException {
    return id;
  }
  
  @Override
  public Entity getType() throws ElException {
    return isConstructor ? parentClass : returnType.getType();
  }
  
  public Entity getParameter(int index) throws ElException {
    return parameters.get(index);
  }

  public void setCode(Code code) {
    this.code = code;
  }

  public void pushCode() {
    EntityStack.code.push(code);
  }
  
  // processing
  
  @Override
  public void addToScope() {
    if(isConstructor) {
      addToScope(paramName(parentClass.name, parameters.size()), this);
    } else {
      addToScope(paramName(name, parameters.size()), this);
    }
  }
  
  @Override
  public void process() throws ElException {
    if(command != null) return;
    startingCommand = vm.VMBase.currentCommand + 1;
    CustomFunction oldFunction = currentFunction;
    currentFunction = this;
    allocateScope();
    for(Variable param: parameters) param.addToScope();
    VMCommand endingCommand = returnType == null ? new vm.call.Return() : null;
    code.processWithoutScope(endingCommand);
    deallocateScope();
    currentFunction = oldFunction;
  }

  @Override
  public void call(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving function call " + toString());
    
    if(isConstructor) {
      if(command != null) {
        resolveParameters(call);
        append(command.create());
        return;
      }
      append(new vm.object.ObjectCreate(parentClass));
      resolveParameters(call);
      append(new vm.call.CallFunction(this));
    } else {
      resolveParameters(call);
      if(command != null) {
        append(command.create());
      } else {
        append(new vm.call.CallFunction(this));
      }
    }
  }

  public void processConstructor(ClassEntity classEntity) throws ElException {
    for(Variable param: parameters)
      param.processField(classEntity, code);
  }

  public void processConstructors() throws ElException {
    code.processConstructors();
  }
  
  public void processCode(VMCommand endingCommand) throws ElException {
    code.processWithoutScope(endingCommand);
  }

  private void resolveParameters(FunctionCall call) throws ElException {
    int i = 0;
    for(Entity parameter: parameters) {
      call.getParameter(i).resolve(parameter.getType().getNativeClass());
      i++;
    }
  }
  
  public void resolveTypes() throws ElException {
    if(returnType != null) returnType = returnType.resolve();
    for(Variable param: parameters) param.resolveType();
  }
  
  public void append(FunctionCall call) throws ElException {
    resolveParameters(call);
    append(command == null ? new CallFunction(this) : command);
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunction(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    deallocateFunction();
    parentClass = classEntity;
    classEntity.addMethod(this, isConstructor);
  }

  @Override
  public void moveToCode(Code code) {
    deallocateFunction();
    code.add(this);
  }

  @Override
  public void moveToBlock() throws ElException {
    deallocateFunction();
  }
  
  // other

  @Override
  public String toString() {
    return isConstructor ? "this" : name.string;// + "." + priority;
  }
  
  @Override
  public void print(StringBuilder indent, String prefix) {
    StringBuilder str = new StringBuilder();
    if(returnType != null) str.append(returnType).append(" ");
    str.append(isConstructor ? "create" : name.string).append("(");
    boolean isNotFirst = false;
    for(Variable parameter : parameters) {
      if(isNotFirst) str.append(", ");
      str.append(parameter.toParamString());
      isNotFirst = true;
    }
    str.append("):").append(allocation);
    if(command == null) {
      code.print(indent, prefix + str);
    } else {
      println(indent + prefix + str + ";");
    }
  }
}
