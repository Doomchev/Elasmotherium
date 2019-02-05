package parser;

public class ActionClear extends Action {
  @Override
  public Action execute() {
    if(log) log("CLEAR");
    tokenStart = textPos;
    prefix = "";
    return nextAction;
  }
}
