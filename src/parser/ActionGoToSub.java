package parser;

import ast.exception.ElException;
import ast.exception.ElException.ActionException;

public class ActionGoToSub extends Action {
  private final Sub sub;
  
  public ActionGoToSub(Sub sub) {
    this.sub = sub;
  }
  
  @Override
  public void execute() throws ElException {
    if(log) log(toString());
    if(sub.action == null) throw new ActionException(this, "", "Sub " + sub.name
        + " is not defined");
    currentAction = sub.action;
  }

  @Override
  public String toString() {
    return "GOTO(" + sub.name + ')';
  }
}
