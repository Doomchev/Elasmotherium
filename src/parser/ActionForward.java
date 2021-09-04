package parser;

import base.ElException;

class ActionForward extends Action {
  @Override
  public Action create(String params) throws ElException {
    return new ActionForward();
  }
  
  @Override
  public void execute() {
    if(log) log(">>");
    incrementTextPos();
    currentAction = nextAction;
  }
}
