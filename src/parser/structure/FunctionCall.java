package parser.structure;

import java.util.Arrays;
import java.util.LinkedList;

public class FunctionCall extends Value {
  public Entity function;
  public final LinkedList<Entity> parameters = new LinkedList<>();

  public FunctionCall(Entity function) {
    this.function = function;
  }

  public FunctionCall(Function function, Entity... params) {
    this.function = function;
    parameters.addAll(Arrays.asList(params));
  }
  
  @Override
  public boolean isEmptyFunction() {
    return function == null || (function.isEmptyFunction() && parameters.isEmpty());
  }
  
  @Override
  public ID getID() {
    return callID;
  }

  @Override
  public ID getFormId() {
    return function.isNativeFunction() ? function.getFormId() : callID;
  }
  
  @Override
  int getPriority() {
    return function == null ? 17 : function.getPriority();
  }

  @Override
  public FunctionCall toCall() {
    return this;
  }

  @Override
  public Entity getChild(ID id) {
    if(id == functionID) return function;
    return null;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return parameters;
  }

  @Override
  public Entity getChild(int index) {
    if(parameters.size() <= index) return null;
    return parameters.get(index);
  }

  @Override
  public Entity getReturnType(Scope parentScope) {
    if(function == EntityStack.ret && parameters.size() >= 1)
      return parameters.getFirst().setTypes(parentScope);
    return null;
  }

  @Override
  public Entity getType() {
    return function.getType();
  }

  @Override
  public Entity setTypes(Scope parentScope) {
    return function.setCallTypes(parameters, parentScope);
  }

  @Override
  public void move(Entity entity) {
    entity.moveToFunctionCall(this);
  }

  @Override
  void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public String toString() {
    return (function == null ? "" : function.toString()) + "("
        + listToString(parameters) + ")";
  }
}
