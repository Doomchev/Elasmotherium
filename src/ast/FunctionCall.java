package ast;

import export.Chunk;
import java.util.Arrays;
import java.util.LinkedList;

public class FunctionCall extends Value {
  public Entity function, type;
  public boolean thisFlag;
  public final LinkedList<Entity> parameters = new LinkedList<>();

  public FunctionCall(Function function) {
    this.function = function;
  }

  public FunctionCall(Function function, Entity... params) {
    this.function = function;
    parameters.addAll(Arrays.asList(params));
  }
  
  @Override
  public boolean isEmptyFunction() {
    return function == null || (function.isEmptyFunction()
        && parameters.isEmpty());
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
  public Chunk getCallForm() {
    return function.getForm();
  }
  
  @Override
  public int getPriority() {
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
  public Entity getType() {
    return type;
  }

  @Override
  public void resolveLinks(Variables variables) {
    function.resolveLinks(this, variables);
  }

  @Override
  public void move(Entity entity) {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public void toByteCode() {
    function.toByteCode(this);
  }

  @Override
  public String toString() {
    return (function == null ? "" : function.toString()) + "("
        + listToString(parameters) + ")";
  }
}
