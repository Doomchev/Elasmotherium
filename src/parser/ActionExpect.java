package parser;

import ast.exception.ElException;
import ast.exception.ElException.ActionException;

public class ActionExpect extends Action {
  private final char symbol;

  public ActionExpect(char symbol) {
    this.symbol = symbol;
  }

  @Override
  public Action create(String params) throws ElException {
    if(params.length() != 3) throw new ActionException(this, 
        "EXPECT", "requires one symbol as parameter");
    return new ActionExpect(params.charAt(1));
  }
  
  @Override
  public void execute() throws ElException {
    if(log) log("EXPECT " + symbol);
    while(true) {
      char c = currentSymbolReader.nextSymbol();
      switch(c) {
        case ' ':
        case '\t':
        case '\r':
        case '\n':
          break;
        default:
          if(c == symbol) {
            currentAction = nextAction;
            return;
          }
          throw new ActionException(this, "EXPECT", symbol + " expected");
      }
    }
  }
}
