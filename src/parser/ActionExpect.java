package parser;

import exception.ElException;
import exception.ElException.ActionException;

public class ActionExpect extends Action {
  private final char symbol;
  private final String errorText;

  public ActionExpect(char symbol, String errorText) {
    this.symbol = symbol;
    this.errorText = errorText;
  }

  @Override
  public Action create(String params) throws ElException {
    String[] paramArray = params.split(",");
    if(paramArray[0].length() != 3 || paramArray.length < 1) {
      throw new ActionException(this, "EXPECT"
          , "requires one symbol and error text as parameters");
    }
    return new ActionExpect(paramArray[0].charAt(1)
        , paramArray.length == 1 ? "" : paramArray[1]);
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
          throw new ActionException(this, "EXPECT"
              , errorText + ", \"" + symbol + "\" expected");
      }
    }
  }
}
