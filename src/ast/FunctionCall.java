package ast;

import base.ElException;
import java.util.LinkedList;
import processor.ProBase;

public class FunctionCall extends Value {
  public static ID resolve = ID.get("resolve");
  
  public Entity function, type;
  public boolean thisFlag;
  public byte priority;
  public final LinkedList<Entity> parameters = new LinkedList<>();

  public FunctionCall(Function function) {
    this.function = function;
    this.priority = function == null ? 17 : function.priority;
  }
  
  @Override
  public byte getPriority() {
    return priority;
  }
  
  // processor fields
  
  @Override
  public Entity getParameter(int index) throws ElException {
    if(index >= parameters.size()) throw new ElException("Parameter number"
        + index + " is not found.");
    return parameters.get(index);
  }
  
  @Override
  public ID getObject() throws ElException {
    return function.getObject();
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    if(log) println(toString());
    ID id = function.getID();
    function = Function.all.get(id);
    if(function != null) {
      currentProcessor.call(function);
    } else {
      function = getFromScope(id);
      if(function == null)
        throw new ElException("Function not found", function);
      currentProcessor.call(this);
    }
  }
  
  @Override
  public void resolveAll() throws ElException {
    int i = 0;
    for(Entity parameter: parameters) {
      currentProcessor.call(parameter, resolve, function.getParameter(i));
      i++;
    }
  }

  // type conversion
  
  @Override
  public FunctionCall toCall() {
    return this;
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
  }

  // other
  
  @Override
  public String toString() {
    return (function == null ? "" : function.toString()) + "("
        + listToString(parameters) + ")";
  }
}
