package parser;

import ast.EntityStack;
import base.ElException;

public class ActionDup extends Action {
  private final EntityStack stack;

  public ActionDup(EntityStack stack) {
    this.stack = stack;
  }

  @Override
  public void execute() throws ElException {
    if(log) log("DUP " + stack.name);
    stack.push(stack.peek());
    currentAction = nextAction;
  }
}
