package parser;

import base.ElException;

class ActionReturn extends Action {
  @Override
  public Action create(String params) throws ElException {
    return new ActionReturn();
  }
  
  @Override
  public void execute() throws ElException {
    if(returnStack.isEmpty()) {
      currentAction = null;
      return;
    }
    
    ActionSub sub = returnStack.pop();
    if(log) {
      log("RETURN to " + sub.parentSub.name);
      subIndent.delete(0, 2);
    } 
    currentAction = sub.nextAction;
  }
}
