package parser;

public class ActionClear extends Action {
  @Override
  public Action execute() {
    if(log) System.out.println(" CLEAR");
    tokenStart = textPos;
    prefix = "";
    return nextAction;
  }
}
