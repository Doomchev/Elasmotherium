package parser;

import base.Base;
import ast.exception.ElException;
import ast.exception.ElException.MethodException;
import ast.exception.EntityException;
import java.util.Stack;

public abstract class Action extends Base {
  static final Stack<ActionSub> returnStack = new Stack<>();
  static Action currentAction;
  
  private final int parserLine;
  Action nextAction;

  public Action() {
    parserLine = currentLineReader == null ? 0 : currentLineReader.getLineNum();
  }
  
  public Action create(String params) throws ElException {
    throw new MethodException(this, "create", "No creating function");
  }
  
  public void setOtherAction(Action action) {}
  
  public abstract void execute() throws ElException, EntityException;

  public Sub getErrorActionSub() {
    while(!returnStack.isEmpty()) {
      ActionSub action = returnStack.pop();
      if(action.errorSub != null) return action.errorSub;
    }
    return null;
  }
  
  public void log(String message) {
    currentSymbolReader.log(parserLine + ": " + message);
  }

  public String errorString() {
    return "parser code (" + parserLine + ")\n";
  }
}
