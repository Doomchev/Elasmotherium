package parser;

import base.ElException;

class ActionLoadPos extends Action {
  @Override
  public ActionLoadPos create(String params) throws ElException {
    return new ActionLoadPos();
  }
  
  @Override
  public void execute() {
    if(log) log("LOADPOS");
    textPos = savedTextPos;
    lineNum = savedLineNum;
    lineStart = savedLineStart;
    currentAction = nextAction;
  }
}
