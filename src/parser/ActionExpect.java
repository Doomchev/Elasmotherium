package parser;

public class ActionExpect extends Action {
  private final char symbol;

  public ActionExpect(char symbol) {
    this.symbol = symbol;
  }

  @Override
  public Action execute() {
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
          if(c == symbol) return nextAction;
          actionError(symbol + " expected");
      }
    }
  }
}
