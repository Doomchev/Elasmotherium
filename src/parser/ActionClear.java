package parser;

public class ActionClear extends Action {
  @Override
  public void execute() {
    if(log) log("CLEAR");
    tokenStart = textPos;
    prefix = "";
    currentAction = nextAction;
  }
}
