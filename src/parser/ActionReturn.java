package parser;

import base.ElException;

class ActionReturn extends Action {
  @Override
  public ActionReturn create(String params) throws ElException {
    return new ActionReturn();
  }
  
  @Override
  public void execute() throws ElException {
    if(returnStack.isEmpty()) throw new ElException(this
        , "RETURN without function call");
    ActionSub sub = returnStack.pop();
    if(log) {
      log("RETURN to " + sub.parentSub.name);
      subIndent = subIndent.substring(2);
    } 
    currentAction = sub.nextAction;
  }
}
