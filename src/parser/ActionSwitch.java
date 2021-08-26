package parser;

import base.ElException;

public abstract class ActionSwitch extends Action {
  public abstract void setStringAction(String str, Action action)
      throws ElException;
  public abstract void setOtherAction(Action action);
  public void setMaskAction(SymbolMask mask, Action action) {};
}
