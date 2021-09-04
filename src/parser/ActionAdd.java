package parser;

import base.ElException;

public class ActionAdd extends Action {
  private final String string;

  public ActionAdd(String string) {
    this.string = string;
  }

  @Override
  public Action create(String params) throws ElException {
    return new ActionAdd(parser.Rules.stringParam(params));
  }
  
  @Override
  public void execute() {
    if(tokenStart < textPos) prefix += text.substring(tokenStart, textPos);
    if(log) log("ADD " + string + " to " + prefix);
    prefix += string;
    currentAction = nextAction;
  }
}
