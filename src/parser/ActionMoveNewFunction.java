package parser;

import ast.Entity;
import ast.Function;
import ast.FunctionCall;

public class ActionMoveNewFunction extends Action {
  private final Function function;
  private final EntityStack<Entity> to;

  public ActionMoveNewFunction(Function function, EntityStack<Entity> to) {
    this.function = function;
    this.to = to;
  }
  
  @Override
  public void execute() throws base.ElException {
    if(log) log("MOVING NEW " + function.getClassName() + " to " + to.name + "("
        + to.peek().toString() + ")");
    to.peek().move(new FunctionCall(function));
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return function.getClassName() + " to " + to.name;
  }
}
