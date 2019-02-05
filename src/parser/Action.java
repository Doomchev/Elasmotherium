package parser;

import base.Base;

public abstract class Action extends ParserBase {
  public static int savedTextPos, savedLineNum, savedLineStart;
  
  public int parserLine;
  public Action nextAction;
  public abstract Action execute();

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
  
  public void actionError(String message) {
    Base.error("Parsing error", currentFileName + " (" + lineNum + ":"
        + (textPos - lineStart) + ")\nparser code (" + parserLine + ")\n"
        + message);
  }
  
  public void log(String message) {
    System.out.println(" " + parserLine + ": " + message);
  }
}
