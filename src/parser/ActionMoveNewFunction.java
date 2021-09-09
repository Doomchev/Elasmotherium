package parser;

import ast.Entity;
import ast.function.FunctionCall;
import ast.function.NativeFunction;

public class ActionMoveNewFunction extends Action {
  private final NativeFunction function;
  private final EntityStack<Entity> to;

  public ActionMoveNewFunction(NativeFunction function
      , EntityStack<Entity> to) {
    this.function = function;
    this.to = to;
  }
  
  @Override
  public void execute() throws base.ElException {
    if(log) log("MOVING NEW " + function + " to " + to.name + "("
        + to.peek().toString() + ")");
    to.peek().move(function);
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return function + " to " + to.name;
  }
}
