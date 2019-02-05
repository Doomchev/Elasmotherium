package parser.structure;

public class StoredNode extends Node {
  private final int index;

  public StoredNode(int index) {
    super(null);
    this.index = index;
  }

  @Override
  public Node resolve() {
    Node node = currentParserScope.variables[index];
    if(node == null) error("Variable at index " + index + " is null");
    return currentParserScope.variables[index].resolve();
  }

  @Override
  public String toString() {
    return "#" + index;
  }
}
