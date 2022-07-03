package ast.function;

import ast.*;
import exception.ElException;
import exception.EntityException;
import exception.EntityException.Cannot;
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

  @Override
  public Entity getType() throws EntityException {
    if(function == NativeFunction.at) {
      return parameters.getFirst().resolve().getSubtype(0);
    }
    return super.getType();
  }

  @Override
  public Entity getSubtype(int index) throws EntityException {
    if(function == NativeFunction.at) {
      return parameters.getFirst().resolve().getSubtype(0);
    }
    return function.resolve().getSubtype(0);
  }

  @Override
  public Variable getField() throws EntityException {
    try {
      if(function == NativeFunction.dot) {
        return parameters.getFirst().getObject()
            .getField(parameters.getLast().getName());
      }
      return super.getField();
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
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
  
  // compiling
  
  @Override
  public void compile() throws EntityException {
    if(log) println(subIndent + toString());
    function.resolveFunction(parameters.size()).compileCall(this);
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
  public void resolveTo(Entity type) throws EntityException {
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
        function.resolveFunction(parameters.size()).resolveCallTo(type, this);
      }
    } catch (EntityException ex) {
      throw new EntityException(function, ex.message);
    } 
  }
  
  @Override
  public Entity resolveLinks() throws EntityException {
    if(function == NativeFunction.dot) {
      parameters.set(0, parameters.getFirst().resolveLinks());
    } else {
      function = function.resolveLinks(parameters.size());
      int index = 0;
      for(Entity parameter: parameters) {
        parameters.set(index, parameter.resolveLinks());
        index++;
      }
    }
    return this;
  }

  protected void resolveParameters(LinkedList<Variable> functionParameters)
      throws EntityException {
    int i = 0, callParametersQuantity = parameters.size();
    for(Entity parameter: functionParameters) {
      Entity functionParameter = callParametersQuantity > i ? parameters.get(i)
          : parameter.getValue();
      functionParameter.resolveTo(parameter.getType());
      i++;
    }
  }

  @Override
  public Entity getObject() throws EntityException {
    Entity func = function.resolveFunction(parameters.size());
    func.compileCall(this);
    if(func == NativeFunction.at) {
      return parameters.getFirst().resolve().getType().getSubtype(0);
    }
    return func.getNativeClass();
  }

  @Override
  public ClassEntity getNativeClass() throws EntityException {
    if(function == NativeFunction.at) {
      return parameters.getFirst().resolve().getSubtype(0).getNativeClass();
    }
    return parameters.getFirst().resolve().getNativeClass();
  }

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
