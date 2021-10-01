package parser;

import ast.exception.ElException;

public class ActionClear extends Action {
  @Override
  public Action create(String params) throws ElException {
    return new ActionClear();
  }
  
  @Override
  public void execute() {
    currentSymbolReader.clear();
    if(log) log("CLEAR");
    currentAction = nextAction;
  }
}
