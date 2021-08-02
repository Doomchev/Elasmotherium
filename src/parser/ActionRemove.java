package parser;

import ast.EntityStack;
import base.ElException;

public class ActionRemove extends Action {
  private final EntityStack stack;

  public ActionRemove(EntityStack stack) {
    this.stack = stack;
  }

  @Override
  public ActionRemove create(String params) throws ElException {
    return new ActionRemove(EntityStack.get(params));
  }
  
  @Override
  public void execute() {
    if(log) log("REMOVE(" + stack.name.string + ")");
    currentAction = this;
    stack.stack.pop();
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return stack.name.string;
  }
}
