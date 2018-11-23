package parser;

public class ActionSwitchSymbol extends Action {
  public Action[] action = new Action[130];


  void setAction(Action action) {
    for(int n = 0; n < 130; n++) if(this.action[n] == null) this.action[n] = action;
  }

  void setAction(char mask, Action action) {
    this.action[mask] = action;
  }

  void setAction(SymbolMask mask, Action action) {
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
    if(currentAction == null) columnError("Unexpected symbol for "
        + currentScope.category.name);
    return currentAction;
  }
}
