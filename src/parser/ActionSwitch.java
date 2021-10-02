package parser;

import exception.ElException;
import exception.ElException.ActionException;
import base.LinkedMap;

public class ActionSwitch extends Action {
  private static class SwitchString extends Action {
    private final LinkedMap<String, Action> cases = new LinkedMap<>();
    private Action other;

    public SwitchString(Action other) {
      this.other = other;
    }
    
    @Override
    public void setOtherAction(Action action) {
      if(other == null) other = action;
    }

    @Override
    public void execute() throws ElException {
      main: for(LinkedMap.Entry<String, Action> entry: cases) {
        for(int i = 0; i < entry.key.length(); i++) {
          if(entry.key.charAt(i) != currentSymbolReader.charAt(1 + i))
            continue main;
        }
        if(log) log("SWITCH TO " + entry.key);
        currentAction = entry.value;
        if(currentAction instanceof ActionForward) {
          if(log) log(">>>");
          currentSymbolReader.incrementTextPos(entry.key.length() + 1);
          currentAction = currentAction.nextAction;
        }
        return;
      }
      currentAction = other;
    }
  }
  
  private final Action[] action = new Action[130];
  
  public void setStringAction(String token, Action action) throws ElException {
    int firstSymbol = token.charAt(0);
    if(token.length() == 1) {
      this.action[firstSymbol] = action;
    } else {
      Action symbolAction = this.action[firstSymbol];
      SwitchString sw = null;
      try {
        sw = (SwitchString) symbolAction;
      } catch(ClassCastException ex) {}
      if(sw == null) {
        sw = new SwitchString(symbolAction);
        this.action[firstSymbol] = sw;
      }
      sw.cases.put(token.substring(1), action);
    }
  }

  @Override
  public void setOtherAction(Action action) {
    for(int n = 0; n < 130; n++)
      if(this.action[n] == null)
        this.action[n] = action;
      else
        this.action[n].setOtherAction(action);
  }

  public void setMaskAction(SymbolMask mask, Action action) {
    for(int n = 0; n < 130; n++)
      if(mask.symbols[n]) this.action[n] = action;
  }

  @Override
  public void execute() throws ElException {
    char currentChar = currentSymbolReader.getChar();
    if(log) {
      if(currentChar == currentSymbolReader.END_OF_FILE)
        log("SWITCH TO END");
      else
        log("SWITCH TO " + currentChar);
    }
    currentAction = action[currentChar];
    if(currentAction == null)
      throw new ActionException(this, "SWITCH:"
          , "No command for " + currentChar);
  }
}
