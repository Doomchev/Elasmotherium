package parser;

import base.ElException;

public class ActionClear extends Action {
  @Override
  public ActionClear create(String params) throws ElException {
    return new ActionClear();
  }
  
  @Override
  public void execute() {
    if(log) log("CLEAR");
    tokenStart = textPos;
    prefix = "";
    currentAction = nextAction;
  }
}
