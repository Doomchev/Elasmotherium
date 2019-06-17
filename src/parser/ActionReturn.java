package parser;

class ActionReturn extends Action {
  @Override
  public Action execute() {
    if(returnStack.isEmpty()) actionError("RETURN without function call");
    ActionSub sub = returnStack.pop();
    if(log) log("RETURN to " + sub.parentSub.name + "\n" + sub.parentSub.name);
    return sub.nextAction;
  }
}
