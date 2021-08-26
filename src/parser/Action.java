package parser;

import java.util.LinkedList;
import ast.ID;
import base.ElException;

public abstract class Action extends ParserBase {
  public static int savedTextPos, savedLineNum, savedLineStart;
  public static final LinkedList<ID> currentFlags = new LinkedList<>();
  public static Action currentAction;
  
  public int parserLine;
  public Action nextAction;

  public Action() {
    parserLine = currentLineNum;
  }
  
  public Action create(String params) throws ElException {
    throw new ElException("No creating funciton for " + toString());
  }
  
  public abstract void execute() throws ElException;

  public Sub getErrorActionSub() {
    while(!returnStack.isEmpty()) {
      ActionSub action = returnStack.pop();
      if(action.errorSub != null) return action.errorSub;
    }
    return null;
  }
  
  public void log(String message) {
    System.out.println(subIndent + parserLine + ": " + message);
  }

  @Override
  public String toString() {
    return getClassName();
  }
}
