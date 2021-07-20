package parser;

class ActionForward extends Action {
  @Override
  public void execute() {
    if(log) log(">>");
    incrementTextPos();
    currentAction = nextAction;
  }
}
