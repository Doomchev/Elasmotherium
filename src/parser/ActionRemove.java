package parser;

import ast.EntityStack;

public class ActionRemove extends Action {
  private final EntityStack stack;

  public ActionRemove(EntityStack stack) {
    this.stack = stack;
  }

  @Override
  public Action execute() {
    if(log) log("REMOVE(" + stack.name.string + ")");
    currentAction = this;
    stack.stack.pop();
    currentAction = null;
    return nextAction;
  }

  @Override
  public String toString() {
    return stack.name.string;
  }
}
