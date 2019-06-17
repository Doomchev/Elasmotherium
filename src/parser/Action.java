package parser;

import base.Base;
import java.util.LinkedList;
import parser.structure.ID;

public abstract class Action extends ParserBase {
  public static int savedTextPos, savedLineNum, savedLineStart;
  public static final LinkedList<ID> currentFlags = new LinkedList<>();
  public static Action currentAction;
  
  public int parserLine;
  public Action nextAction;
  public abstract Action execute();

  public Sub getErrorActionSub() {
    while(!returnStack.isEmpty()) {
      ActionSub action = returnStack.pop();
      if(action.errorSub != null) return action.errorSub;
    }
    return null;
  }
  
  public void actionError(String message) {
    Base.error("Parsing error", currentFileName + " (" + lineNum + ":"
        + (textPos - lineStart) + ")\nparser code (" + parserLine + ")\n"
        + message);
  }
  
  public void log(String message) {
    System.out.println(" " + parserLine + ": " + message);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
