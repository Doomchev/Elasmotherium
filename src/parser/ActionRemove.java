package parser;

import exception.ElException;

public class ActionRemove extends Action {
  private final EntityStack stack;

  public ActionRemove(EntityStack stack) {
    this.stack = stack;
  }

  @Override
  public Action create(String params) throws ElException {
    return new ActionRemove(EntityStack.get(params));
  }
  
  @Override
  public void execute() throws ElException {
    if(log) log("REMOVE(" + stack.name.string + ")");
    currentAction = this;
    stack.pop();
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return stack.name.string;
  }
}
