package parser;

import base.ElException;

class ActionSkip extends Action {
  @Override
  public ActionSkip create(String params) throws ElException {
    return new ActionSkip();
  }
  
  @Override
  public void execute() {
    if(log) log("SKIP");
    if(tokenStart < textPos) prefix += text.substring(tokenStart, textPos);
    incrementTextPos();
    tokenStart = textPos;
    currentAction = nextAction;
  }
}
