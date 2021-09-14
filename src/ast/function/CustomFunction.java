package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import static ast.Entity.append;
import ast.ID;
import ast.Variable;
import base.ElException;
import base.ElException.Cannot;
import java.util.LinkedList;
import parser.EntityStack;
import vm.VMCommand;
import vm.call.CallFunction;

public abstract class CustomFunction extends Function {
  protected final LinkedList<Variable> parameters = new LinkedList<>();
  protected Code code = new Code();
  protected int startingCommand;
  protected int allocation = 0;
  protected VMCommand command = null;
  
  // constructors
  
  public CustomFunction(ID name) {
    super(name);
  }
  
  // parameters

  public int getCallAllocation() {
    return parameters.size() - 1;
  }

  public int getCallDeallocation() {
    return parameters.size();
  }

  public int getStartingCommand() {
    return startingCommand;
  }

  @Override
  public int getParametersQuantity() {
    return parameters.size();
  }

  public void setReturnType(Entity returnType) throws ElException {
    throw new Cannot("set return type of", this);
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
  
  public StaticFunction getFunction(ID id, int parametersQuantity)
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
    addToScope(name, this, parameters.size());
  }
  
  @Override
  public void process() throws ElException {
    if(command != null) return;
    startingCommand = vm.VMBase.currentCommand + 1;
    CustomFunction oldFunction = currentFunction;
    currentFunction = this;
    allocateScope();
    for(Variable param: parameters) param.addToScope();
    code.processWithoutScope(getEndingCommand());
    deallocateScope();
    currentFunction = oldFunction;
  }
  
  public VMCommand getEndingCommand() throws ElException {
    return new vm.call.Return();
  }

  @Override
  public void call(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving function call " + toString());
    resolveParameters(call);
    if(command != null) {
      append(command.create());
    } else {
      append(new vm.call.CallFunction(this));
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

  protected void resolveParameters(FunctionCall call) throws ElException {
    int i = 0;
    for(Entity parameter: parameters) {
      call.getParameter(i).resolve(parameter.getType().getNativeClass());
      i++;
    }
  }
  
  public abstract void resolveTypes() throws ElException;
  
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
  public void moveToBlock() throws ElException {
    deallocateFunction();
  }
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
  
  public void print(StringBuilder indent, String prefix, String name) {
    StringBuilder str = new StringBuilder();
    str.append(name).append("(");
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
  
  @Override
  public void print(StringBuilder indent, String prefix) {
    print(indent, prefix, name.string);
  }

}
