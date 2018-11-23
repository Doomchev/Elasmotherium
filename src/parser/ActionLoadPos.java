package parser;

class ActionLoadPos extends Action {
  @Override
  public Action execute() {
    if(log) System.out.println(" LOADPOS");
    textPos = savedTextPos;
    lineNum = savedLineNum;
    lineStart = savedLineStart;
    return nextAction;
  }
}
