package parser;

public class StoredNode extends Node {
  private final int index;

  public StoredNode(int index) {
    super(null);
    this.index = index;
  }

  @Override
  public Node resolve() {
    Node node = currentScope.variables[index];
    if(node == null) columnError("Variable at index " + index + " is null");
    return currentScope.variables[index].resolve();
  }

  @Override
  public String toString() {
    return "#" + index;
  }
}
