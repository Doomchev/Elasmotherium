package parser;

import ast.ID;
import exception.ElException;

public class ActionDup extends Action {
  private final EntityStack stack;

  public ActionDup(EntityStack stack) {
    this.stack = stack;
  }

  @Override
  public Action create(String params) throws ElException {
    return new ActionDup(EntityStack.all.get(ID.get(params.trim())));
  }
  
  @Override
  public void execute() throws ElException {
    if(log) log("DUP " + stack.name + " (" + stack.peek() + ")");
    stack.push(stack.peek());
    currentAction = nextAction;
  }
}
