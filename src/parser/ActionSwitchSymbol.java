package parser;

import base.ElException;

public class ActionSwitchSymbol extends ActionSwitch {
  public Action[] action = new Action[130];

  @Override
  public void setStringAction(String token, Action action) throws ElException {
    for(int i = 0; i < token.length(); i++)
      this.action[token.charAt(i)] = action;
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
  public void execute() throws ElException {
    if(textPos >= textLength) {
      currentChar = 129;
      if(log) log("SWITCH TO END");
    } else {
      currentChar = text.charAt(textPos);
      if(log) log("SWITCH TO " + currentChar);
      currentChar = currentChar < 128 ? currentChar : 128;
    }
    currentAction = action[currentChar];
    if(currentAction == null) throw new ElException("No command for "
        + currentChar);
  }
}
