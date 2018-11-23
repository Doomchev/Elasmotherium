package parser;

public class ActionExpect extends Action {
  private final char symbol;

  public ActionExpect(char symbol) {
    this.symbol = symbol;
  }

  @Override
  public Action execute() {
    if(log) System.out.println(" EXPECT " + symbol);
    while(true) {
      char c = text.charAt(textPos);
      textPos++;
      switch(c) {
        case ' ':
        case '\t':
        case '\r':
        case '\n':
          break;
        default:
          if(c == symbol) return nextAction;
          columnError(symbol + " expected");
      }
    }
  }
}
