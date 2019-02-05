package parser;

class ActionForward extends Action {
  @Override
  public Action execute() {
    if(log) log(">>");
    incrementTextPos();
    return nextAction;
  }
}
