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
      log("SUB(" + sub.name + ")\n" + sub.name);
    }
    parserScopes.push(currentParserScope);
    currentParserScope = new ParserScope(sub, this);
    return sub.action;
  }

  @Override
  public String toString() {
    return sub.name;
  }
}
