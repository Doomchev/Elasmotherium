package parser;

public abstract class ActionSwitch extends Action {
  public abstract void setStringAction(String str, Action action);
  public abstract void setOtherAction(Action action);
  public void setMaskAction(SymbolMask mask, Action action) {};
  public void setCategoryAction(Category category, Action action) {};
}
