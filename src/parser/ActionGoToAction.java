package parser;

import base.ElException;

public class ActionGoToAction extends Action {
  private final Action action;
  
  public ActionGoToAction(Action action) {
    this.action = action;
  }
  
  @Override
  public void execute() {
    currentAction = action;
  }

  @Override
  public String toString() {
    return " GO TO ACTION";
  }
}
