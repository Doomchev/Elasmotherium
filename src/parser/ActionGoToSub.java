package parser;

public class ActionGoToSub extends Action {
  private final Sub sub;
  
  public ActionGoToSub(Sub sub) {
    this.sub = sub;
  }
  
  @Override
  public Action execute() {
    if(log) log(toString() + "\n" + sub.name);
    if(sub.action == null) actionError("Sub " + sub.name + " is not defined.");
    return sub.action;
  }

  @Override
  public String toString() {
    return "GOTO(" + sub.name + ')';
  }
}
