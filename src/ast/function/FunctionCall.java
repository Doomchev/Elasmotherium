package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import ast.ID;
import ast.Value;
import ast.Variable;
import static base.Base.currentProcessor;
import base.ElException;
import base.ElException.Cannot;
import base.ElException.NotFound;
import java.util.LinkedList;
import processor.Processor;

public class FunctionCall extends Value {
  public static final ID id = ID.get("call");
  
  private Entity function;
  private final LinkedList<Entity> parameters = new LinkedList<>();
  
  // creating

  public FunctionCall(NativeFunction function) {
    this.function = function;
  }
  
  // parameters

  public void add(Entity value) {
    parameters.add(value);
  }

  public void add(LinkedList<Value> values) {
    parameters.addAll(values);
  }

  public void addFirst(Entity value) {
    parameters.addFirst(value);
  }

  public int getParametersQuantity() throws ElException {
    return parameters.size();
  }
  
  @Override
  public Entity getType() throws ElException {
    return function.getType();
  }

  public Entity getFunction() {
    return function;
  }
  
  public void setFunction(Entity function) {
    this.function = function;
  }  
  
  // processor fields
  
  public Entity getParameter(int index) throws ElException {
    if(index >= parameters.size())
      throw new NotFound(this, "Parameter number " + index);
    return parameters.get(index);
  }

  public void setParameter(int index, Entity value) {
    parameters.set(index, value);
  }
  
  @Override
  public ID getID() throws ElException {
    return function instanceof NativeFunction ? function.getID() : id;
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    if(log) println(subIndent + toString());
    function.resolveFunction(parameters.size()).process(this);
  }
  
  @Override
  public Entity resolveFunction(int parametersQuantity) throws ElException {
    int thisParametersQuantity = parameters.size();
    if(function == NativeFunction.dot) {
      if(parameters.size() != 2)
        throw new Cannot("resolve", this);
      Entity type = parameters.getFirst().getObject();
      return type.getMethod(parameters.getLast().getName(), parametersQuantity);
    }
    Entity func = function.resolveFunction(thisParametersQuantity);
    func.process(this);
    return func.getType();
  }

  @Override
  public void resolve(ClassEntity parameter) throws ElException {
    if(function instanceof NativeFunction) {
      currentProcessor.resolveCall(this, function.getName(), parameter);
    } else {
      function.resolveFunction(parameters.size()).resolve(parameter, this);
    }
  }
  
  @Override
  public Entity resolveRecursively() throws ElException {
    function = function.resolveRecursively(parameters.size());
    int index = 0;
    for(Entity parameter: parameters) {
      parameters.set(index, parameter.resolveRecursively());
      index++;
    }
    return this;
  }

  protected void resolveParameters(LinkedList<Variable> functionParameters)
      throws ElException {
    int i = 0, callParametersQuantity = parameters.size();
    for(Entity parameter: functionParameters) {
      (callParametersQuantity > i ? parameters.get(i)
          : parameter.getValue()).resolve(parameter.getType().getNativeClass());
      i++;
    }
  }

  @Override
  public ClassEntity getObject() throws ElException {
    Entity func = function.resolveFunction(parameters.size());
    func.process(this);
    return func.getNativeClass();
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.addLine(this);
  }

  // other
  
  @Override
  public String toString() {
    return function + "(" + listToString(parameters) + ")";
  }
}
