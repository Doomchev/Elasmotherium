package ast.function;

import ast.*;
import exception.ElException;
import exception.EntityException;
import exception.NotFound;

import java.util.LinkedList;

public class FunctionCall extends Value {
  public static final ID id = ID.get("call");
  
  private Entity function;
  private final LinkedList<Entity> parameters = new LinkedList<>();
  
  // creating

  public FunctionCall(NativeFunction function) {
    super(0, 0);
    this.function = function;
  }
  
  // child objects

  public void add(Entity value) {
    if(textEnd == 0) {
      textStart = value.textStart;
      textEnd = value.textEnd;
    }
    parameters.add(value);
  }

  public void add(LinkedList<Value> values) {
    parameters.addAll(values);
  }

  public void addFirst(Entity value) {
    parameters.addFirst(value);
  }
  
  // properties
  
  @Override
  public ID getID() throws EntityException {
    return function instanceof NativeFunction ? function.getID() : id;
  }

  public Entity getType() throws EntityException {
    if(function == NativeFunction.at) {
      return parameters.getFirst().getSubType();
    }
    return function.getType();
  }
  
  @Override
  public Entity getType(Entity[] subTypes) throws EntityException {
    return function.getType(subTypes);
  }

  @Override
  public Entity getChild(ID name) throws EntityException {
    try {
      if(function == NativeFunction.dot) {
        return parameters.getFirst().getObject().getChild(parameters.getLast()
            .getName());
      } else if(function == NativeFunction.at) {
        return parameters.getFirst().getSubType();
      }
      return super.getField();
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
  }

  @Override
  public Entity getSubType() throws EntityException {
    return parameters.getFirst().getSubType();
  }

  public Entity getFunction() {
    return function;
  }
  
  public void setFunction(Entity function) {
    this.function = function;
  }
  
  public Entity getParameter(int index) throws NotFound {
    if(index >= parameters.size())
      throw new NotFound("Parameter number " + index, this);
    return parameters.get(index);
  }

  public void setParameter(int index, Entity value) {
    parameters.set(index, value);
  }

  @Override
  public Entity getErrorEntity() {
    return function.getErrorEntity();
  }

  // resolving

  private void resolveParameters(int parametersQuantity) throws EntityException {
    function = function.resolveFunction(parametersQuantity);
    int index = 0;
    for(Entity parameter: parameters) {
      parameters.set(index, parameter.resolveEntity());
      index++;
    }
  }

  @Override
  public void resolveLinks() throws EntityException {
    println(toString());
    if(function == NativeFunction.dot) {
      Entity param0 = parameters.getFirst();
      Entity object = param0.resolveEntity();
      parameters.set(0, object);
      try {
        parameters.set(1, object.getChild(parameters.getLast().getName()));
      } catch(NotFound ex) {
        throw new EntityException(this, ex.message);
      }
    } else if(function == NativeFunction.at) {
      parameters.set(0, parameters.getFirst().resolveEntity());
      parameters.set(1, parameters.getLast().resolveEntity());
    } else {
      resolveParameters(parameters.size());
    }
  }

  @Override
  public Entity resolveFunction(int parametersQuantity) throws EntityException {
    if(function == NativeFunction.dot) {
      Entity param0 = parameters.getFirst();
      Entity object = param0.resolveEntity();
      parameters.set(0, object);
      try {
        parameters.set(1, object.getType().getMethod(parameters.getLast()
            .getName(), parametersQuantity));
      } catch(NotFound ex) {
        throw new EntityException(this, ex.message);
      }
    } else {
      resolveParameters(parametersQuantity);
    }
    return this;
  }

  @Override
  public Entity resolveEntity() throws EntityException {
    resolveLinks();
    return this;
  }

  // compiling
  
  /*@Override
  public void compile() throws EntityException {
    if(log) println(subIndent + toString());
    function = function.resolveFunction(parameters.size());
    function.compileCall(this);
  }
  
  @Override
  public Entity resolveFunction(int parametersQuantity)
      throws EntityException {
    int thisParametersQuantity = parameters.size();
    if(function == NativeFunction.dot) {
      if(parameters.size() != 2)
        throw new Cannot("resolve", this);
      Entity type = parameters.getFirst().getObject();
      try {
        return type.getMethod(parameters.getLast().getName()
            , parametersQuantity);
      } catch(NotFound ex) {
        throw new EntityException(this, ex.message);
      }
    }
    Entity func = function.resolveFunction(thisParametersQuantity);
    func.compileCall(this);
    throw new EntityException.NotImplemented(this, "resolveFunction");
    //return func.getType(null);
  }

  @Override
  public void resolve(Entity type) throws EntityException {
    try {
      if(function == NativeFunction.dot) {
        parameters.getFirst().getObject()
            .resolveField(parameters.getLast().getName(), type);
      } else if(function instanceof NativeFunction) {
        try {
          currentProcessor.resolveCall(this, function.getName(), type);
        } catch (ElException ex) {
          throw new EntityException(this, ex.message);
        }
      } else {
        function.resolveFunction(parameters.size()).resolve(type, this);
      }
    } catch (EntityException ex) {
      throw new EntityException(function, ex.message);
    } 
  }

  protected void resolveParameters(LinkedList<Variable> functionParameters)
      throws EntityException {
    int i = 0, callParametersQuantity = parameters.size();
    for(Entity parameter: functionParameters) {
      (callParametersQuantity > i ? parameters.get(i)
          : parameter.getValue()).resolve(parameter.getType());
      i++;
    }
  }

  protected void resolveParameters(LinkedList<Variable> functionParameters
      , Entity[] subTypes)
      throws EntityException {
    int i = 0, callParametersQuantity = parameters.size();
    for(Entity parameter: functionParameters) {
      (callParametersQuantity > i ? parameters.get(i)
          : parameter.getValue()).resolve(parameter.getType(subTypes));
      i++;
    }
  }

  @Override
  public ClassEntity getObject() throws EntityException {
    Entity func = function.resolveFunction(parameters.size());
    func.compileCall(this);
    return func.getNativeClass();
  }

  @Override
  public ClassEntity getNativeClass() throws EntityException {
    return parameters.get(0).getNativeClass();
  }*/

  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.addLine(this);
  }

  // other
  
  @Override
  public String toString() {
    return function == null ? "" : function.toString(parameters);
  }
}
