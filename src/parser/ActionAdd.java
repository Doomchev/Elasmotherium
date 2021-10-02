package parser;

import exception.ElException;

public class ActionAdd extends Action {
  private final String string;

  public ActionAdd(String string) {
    this.string = string;
  }

  @Override
  public Action create(String params) throws ElException {
    return new ActionAdd(stringParam(params));
  }
  
  @Override
  public void execute() {
    currentSymbolReader.add(string);
    if(log) log("ADD " + string);
    currentAction = nextAction;
  }
}
