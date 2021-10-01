package parser;

import ast.exception.ElException;

class ActionSkip extends Action {
  @Override
  public Action create(String params) throws ElException {
    return new ActionSkip();
  }
  
  @Override
  public void execute() {
    currentSymbolReader.skip();
    if(log) log("SKIP");
    currentAction = nextAction;
  }
}
