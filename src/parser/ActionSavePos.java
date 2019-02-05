package parser;

class ActionSavePos extends Action {
  @Override
  public Action execute() {
    if(log) log("SAVEPOS");
    savedTextPos = textPos;
    savedLineNum = lineNum;
    savedLineStart = lineStart;
    return nextAction;
  }
}
