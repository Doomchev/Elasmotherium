package parser.structure;

import export.Chunk;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import vm.Command;
import vm.I64Allocate;
import vm.VMBase;

public class FunctionCall extends Value {
  public Function function;
  public ID functionName;
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
    return function.getType();
  }

  @Override
  public void addToScope(Scope scope) {
    if(parameters.isEmpty()) return;
    parameters.getFirst().addToScope(scope);
  }

  @Override
  public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    setTypes(parentScope);
  }

  @Override
  public void setTypes(Scope parentScope) {
    if(functionName != null) {
      Entity functionEntity = parentScope.get(functionName, thisFlag);
      if(functionEntity == null) error(functionName + " is not found");
      function = functionEntity.toFunction();
    }
    for(Entity parameter : parameters) parameter.setTypes(parentScope);
    function.setCallTypes(parameters, parentScope);
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
  public void toByteCode() {
    function.toByteCode(this);
  }

  @Override
  public String toString() {
    return (function == null ? "" : function.toString()) + "("
        + listToString(parameters) + ")";
  }
}
