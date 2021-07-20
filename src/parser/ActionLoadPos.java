package parser;

class ActionLoadPos extends Action {
  @Override
  public void execute() {
    if(log) log("LOADPOS");
    textPos = savedTextPos;
    lineNum = savedLineNum;
    lineStart = savedLineStart;
    currentAction = nextAction;
  }
}
