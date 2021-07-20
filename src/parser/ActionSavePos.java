package parser;

class ActionSavePos extends Action {
  @Override
  public void execute() {
    if(log) log("SAVEPOS");
    savedTextPos = textPos;
    savedLineNum = lineNum;
    savedLineStart = lineStart;
    currentAction = nextAction;
  }
}
