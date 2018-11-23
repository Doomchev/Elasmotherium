package parser;

public class ActionGoToAction extends Action {
  private final Action action;
  
  public ActionGoToAction(Action action) {
    this.action = action;
  }
  
  @Override
  public Action execute() {
    return action;
  }

  @Override
  public String toString() {
    return " GO TO ACTION";
  }
}
