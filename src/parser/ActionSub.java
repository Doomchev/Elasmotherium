package parser;

public class ActionSub extends Action {
  public final Sub sub, parentSub, errorSub;

  public ActionSub(Sub sub, Sub parentSub, Sub errorSub) {
    this.sub = sub;
    this.parentSub = parentSub;
    this.errorSub = errorSub;
  }

  @Override
  public Action execute() {
    if(log) log("SUB " + sub.name + (errorSub == null ? "" : " ON ERROR " 
        + errorSub.name) + "\n" + sub.name);
    returnStack.push(this);
    return sub.action;
  }

  @Override
  public String toString() {
    return sub.name;
  }
}
