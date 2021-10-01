package parser;

import ast.exception.ElException;

class ActionLoadPos extends Action {
  @Override
  public Action create(String params) throws ElException {
    return new ActionLoadPos();
  }
  
  @Override
  public void execute() {
    if(log) log("LOADPOS");
    currentSymbolReader.loadPos();
    currentAction = nextAction;
  }
}
