package parser;

import parser.structure.Node;

public class ActionStore extends Action {
  private final int index;

  public ActionStore(int index) {
    this.index = index;
  }

  @Override
  public Action execute() {
    String txt = prefix + text.substring(tokenStart, textPos);
    if(log) log("STORE(" + index + ",\"" + txt + "\")");
    Node node = new Node(currentParserScope.category, txt);
    currentParserScope.variables[index] = node;
    prefix = "";
    tokenStart = textPos;
    return nextAction;
  }

  @Override
  public String toString() {
    return "" + index;
  }
}
