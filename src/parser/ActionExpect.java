package parser;

import base.ElException;

public class ActionExpect extends Action {
  private final char symbol;

  public ActionExpect(char symbol) {
    this.symbol = symbol;
  }

  @Override
  public ActionExpect create(String params) throws ElException {
    if(params.length() != 3) throw new ElException(
        "EXPECT command requires one symbol" + " as parameter");
    return new ActionExpect(params.charAt(1));
  }
  
  @Override
  public void execute() throws ElException {
    if(log) log("EXPECT " + symbol);
    while(true) {
      char c = text.charAt(textPos);
      incrementTextPos();
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
          throw new ElException(this, symbol + " expected");
      }
    }
  }
}
