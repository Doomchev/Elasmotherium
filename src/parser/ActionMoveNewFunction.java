package parser;

import ast.Entity;
import ast.EntityStack;
import ast.FunctionCall;
import ast.NativeFunction;

public class ActionMoveNewFunction extends Action {
  private final NativeFunction function;
  private final EntityStack<Entity> to;

  public ActionMoveNewFunction(NativeFunction function, EntityStack<Entity> to) {
    this.function = function;
    this.to = to;
  }
  
  @Override
  public Action execute() {
    if(log) log("MOVING NEW " + function.getName() + " to " + to.name + "("
        + to.peek().toString() + ")");
    to.peek().move(new FunctionCall(function));
    return nextAction;
  }

  @Override
  public String toString() {
    return function.getName() + " to " + to.name;
  }
}
