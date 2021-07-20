package ast;

import java.util.Arrays;
import java.util.LinkedList;

public class FunctionCall extends Value {
  public Entity function, type;
  public boolean thisFlag;
  public byte priority = 0;
  public final LinkedList<Entity> parameters = new LinkedList<>();

  public FunctionCall(Function function) {
    this.function = function;
  }

  public FunctionCall(Function function, Entity... params) {
    this.function = function;
    parameters.addAll(Arrays.asList(params));
  }
  
  @Override
  public ID getID() {
    return callID;
  }
  
  @Override
  public byte getPriority() {
    return priority;
  }

  @Override
  public FunctionCall toCall() {
    return this;
  }

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
  }

  @Override
  public String toString() {
    return (function == null ? "" : function.toString()) + "("
        + listToString(parameters) + ")";
  }
}
