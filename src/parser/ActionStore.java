package parser;

public class ActionStore extends Action {
  private final int index;

  public ActionStore(int index) {
    this.index = index;
  }

  @Override
  public Action execute() {
    String txt = prefix + text.substring(tokenStart, textPos);
    if(log) System.out.println(" STORE(" + index + ",\"" + txt + "\")");
    Node node = new Node(currentScope.category, txt);
    currentScope.variables[index] = node;
    prefix = "";
    tokenStart = textPos;
    return nextAction;
  }

  @Override
  public String toString() {
    return "" + index;
  }
}
