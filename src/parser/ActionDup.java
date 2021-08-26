package parser;

import ast.ID;
import base.ElException;

public class ActionDup extends Action {
  private final EntityStack stack;

  public ActionDup(EntityStack stack) {
    this.stack = stack;
  }

  @Override
  public ActionDup create(String params) throws ElException {
    return new ActionDup(EntityStack.all.get(ID.get(params.trim())));
  }
  
  @Override
  public void execute() throws ElException {
    if(log) log("DUP " + stack.name);
    stack.push(stack.peek());
    currentAction = nextAction;
  }
}
