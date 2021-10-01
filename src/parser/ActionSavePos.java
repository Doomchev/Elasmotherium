package parser;

import ast.exception.ElException;

class ActionSavePos extends Action {
  @Override
  public Action create(String params) throws ElException {
    return new ActionSavePos();
  }
  
  @Override
  public void execute() {
    if(log) log("SAVEPOS");
    currentSymbolReader.savePos();
    currentAction = nextAction;
  }
}
