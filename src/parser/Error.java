package parser;

import base.ElException;

public class Error extends Action {
  private final String errorText;

  public Error(String text) {
    this.errorText = text;
  }
  
  public Error derive(String param) throws ElException {
    return new Error(errorText.replace("\\0", param));
  }

  @Override
  public ActionClear create(String params) throws ElException {
    return new ActionClear();
  }
  
  @Override
  public void execute() throws ElException {
    Sub errorActionSub = getErrorActionSub();
    if(errorActionSub == null) throw new ElException(this, errorText);
    if(log) log("ERROR - RETURNING TO " + errorActionSub.name);
    subIndent = subIndent.substring(2);
    currentAction = errorActionSub.action;
  }
}
