package parser;

public class ActionSub extends Action {
  private final Category sub;
  public final int storingIndex;

  public ActionSub(Category sub, int index) {
    this.sub = sub;
    this.storingIndex = index;
  }

  @Override
  public Action execute() {
    if(log) {
      System.out.println(" SUB(" + sub.name + ")");
      System.out.println(sub.name);
    }
    scopes.push(currentScope);
    currentScope = new Scope(sub, this);
    return sub.action;
  }

  @Override
  public String toString() {
    return sub.name;
  }
}
