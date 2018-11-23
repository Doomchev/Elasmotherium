package parser;

class ActionSavePos extends Action {
  @Override
  public Action execute() {
    if(log) System.out.println(" SAVEPOS");
    savedTextPos = textPos;
    savedLineNum = lineNum;
    savedLineStart = lineStart;
    return nextAction;
  }
}
