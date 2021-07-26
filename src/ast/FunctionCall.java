package ast;

import java.util.LinkedList;

public class FunctionCall extends Value {
  public Entity function, type;
  public boolean thisFlag;
  public byte priority;
  public final LinkedList<Entity> parameters = new LinkedList<>();

  public FunctionCall(Function function) {
    this.function = function;
    this.priority = function == null ? 17 : function.priority;
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
