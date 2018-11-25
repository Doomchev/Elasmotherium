package parser;

public class ActionSwitchSymbol extends ActionSwitch {
  public Action[] action = new Action[130];

  @Override
  public void setStringAction(String token, Action action) {
    if(token.length() != 1) parsingCodeError("Invalid token");
    this.action[token.charAt(0)] = action;
  }

  @Override
  public void setOtherAction(Action action) {
    for(int n = 0; n < 130; n++) if(this.action[n] == null) this.action[n] = action;
  }

  @Override
  public void setMaskAction(SymbolMask mask, Action action) {
    for(int n = 0; n < 130; n++) if(mask.symbols[n]) this.action[n] = action;
  }

  @Override
  public Action execute() {
    if(textPos >= textLength) {
      currentChar = 129;
    } else {
      currentChar = text.charAt(textPos);
      currentChar = currentChar < 128 ? currentChar : 128;
    }
    if(log) System.out.println(" SWITCH TO " + currentChar);
    Action currentAction = action[currentChar];
    if(currentAction == null) parsingError("Unexpected symbol for "
        + currentScope.category.name);
    return currentAction;
  }
}
