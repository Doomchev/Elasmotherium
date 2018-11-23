package parser;

class ActionSkip extends Action {
  @Override
  public Action execute() {
    if(log) System.out.println(" SKIP");
    if(tokenStart < textPos) prefix += text.substring(tokenStart, textPos);
    incrementTextPos();
    tokenStart = textPos;
    return nextAction;
  }
}
