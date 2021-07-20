package parser;

class ActionSkip extends Action {
  @Override
  public void execute() {
    if(log) log("SKIP");
    if(tokenStart < textPos) prefix += text.substring(tokenStart, textPos);
    incrementTextPos();
    tokenStart = textPos;
    currentAction = nextAction;
  }
}
