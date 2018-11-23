package parser;

class ActionForward extends Action {
  @Override
  public Action execute() {
    if(log) System.out.println(" >>");
    incrementTextPos();
    return nextAction;
  }
}
