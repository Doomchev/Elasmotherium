package parser;

import base.ElException;

class ActionSavePos extends Action {
  @Override
  public ActionSavePos create(String params) throws ElException {
    return new ActionSavePos();
  }
  
  @Override
  public void execute() {
    if(log) log("SAVEPOS");
    savedTextPos = textPos;
    savedLineNum = lineNum;
    savedLineStart = lineStart;
    currentAction = nextAction;
  }
}
