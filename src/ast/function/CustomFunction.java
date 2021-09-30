package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import ast.ID;
import ast.IDEntity;
import ast.Variable;
import base.ElException;
import base.ElException.Cannot;
import java.util.LinkedList;
import parser.EntityStack;
import vm.VMCommand;

public abstract class CustomFunction extends Function {
  protected final LinkedList<Variable> parameters = new LinkedList<>();
  protected Code code = new Code();
  protected int startingCommand;
  protected int allocation = 0;
  protected int fromParametersQuantity = 0, toParametersQuantity = 0;
  protected VMCommand command = null;
  
  // constructors
  
  public CustomFunction() {
    super(null, 0, 0);
  }
  
  public CustomFunction(IDEntity name) {
    super(name);
  }
  
  // properties

  public int getCallAllocation() {
    return parameters.size() - 1;
  }

  public int getCallDeallocation() {
    return parameters.size();
  }

  public int getStartingCommand() {
    return startingCommand;
  }

  public void setReturnType(Entity returnType) throws ElException {
    throw new Cannot("set return type of", this);
  }

  @Override
  public boolean isFunction(ID name, int parametersQuantity)
      throws ElException {
    return this.name == name
        && fromParametersQuantity <= parametersQuantity
        && parametersQuantity <= toParametersQuantity;
  }
  
  @Override
  public boolean isValue(ID name)
      throws ElException {
    return this.name == name && fromParametersQuantity == 0;
  }
  
  // allocation

  public void setAllocation() {
    allocation = Math.max(allocation, currentAllocation);
  }

  public void printAllocation(String fileName) {
    code.print(new StringBuilder(), fileName + ":" + allocation + " ");
  }
  
  // child objects

  public int addParameter(Variable variable) throws ElException {
    int index = allocation;
    allocation++;
    parameters.add(variable);
    toParametersQuantity = parameters.size();
    if(variable.getValue() == null)
      fromParametersQuantity = toParametersQuantity;
    return index;
  } 
  
  public StaticFunction getFunction(ID id, int parametersQuantity)
      throws ElException {
    return code.getFunction(id, parametersQuantity);
  }
  
  public void setCommand(VMCommand command) {
    this.command = command;
  }
  
  // properties

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
  
  // preprocessing

  public void processConstructor(ClassEntity classEntity) throws ElException {
    for(Variable param: parameters)
      param.processField(classEntity, code);
  }

  public void processConstructors() throws ElException {
    code.processConstructors();
  }
  
  public abstract void resolveTypes() throws ElException;
  
  // processing
  
  @Override
  public void process() throws ElException {
    if(command != null) return;
    startingCommand = vm.VMBase.currentCommand + 1;
    CustomFunction oldFunction = currentFunction;
    currentFunction = this;
    allocateScope();
    for(Variable parameter: parameters) addToScope(parameter);
    code.processWithoutScope(getEndingCommand());
    deallocateScope();
    currentFunction = oldFunction;
  }
  
  public VMCommand getEndingCommand() throws ElException {
    return new vm.call.Return();
  }
  
  public void processCode(VMCommand endingCommand) throws ElException {
    code.processWithoutScope(endingCommand);
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
