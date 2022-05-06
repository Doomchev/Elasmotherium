package parser;

import exception.ElException;
import exception.ElException.ActionException;

public class Error extends Action {
  private final String errorText;

  public Error(String text) {
    this.errorText = text;
  }
  
  public Error derive(String param) {
    return new Error(errorText.replace("\\0", param));
  }

  @Override
  public Action create(String params) throws ElException {
    return new ActionClear();
  }
  
  @Override
  public void execute() throws ElException {
    Sub errorActionSub = getErrorActionSub();
    if(errorActionSub == null) throw new ActionException(this, "ERROR"
        , errorText.replace('$', currentSymbolReader.getChar()));
    if(log) log("ERROR - RETURNING TO " + errorActionSub.name);
    subIndent.delete(0, 2);
    currentAction = errorActionSub.action;
  }
}
