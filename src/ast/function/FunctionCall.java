package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import ast.ID;
import ast.Value;
import static base.Base.currentProcessor;
import base.ElException;
import base.ElException.NotFound;
import java.util.LinkedList;

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
    if(function == null) return function.getName();
    function = function.resolve(parameters.size());
    return function.getID();
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    if(log) println(toString());
    function.call(this);
  }
  
  @Override
  public void call(FunctionCall call) throws ElException {
    if(call == null) {
      function.call(this);
    } else {
      currentProcessor.call(this, call);
    }
  }

  @Override
  public void resolve(ClassEntity parameter) throws ElException {
    function = function.resolve(parameters.size());
    currentProcessor.resolve(
        function instanceof FunctionCall ? function : this
        , function.getID(), this, parameter);
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
