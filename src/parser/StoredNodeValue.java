package parser;

public class StoredNodeValue extends Node {
  private final int index;

  public StoredNodeValue(int index, Category type) {
    super(null);
    this.index = index;
    this.type = type;
  }

  @Override
  public Node resolve() {
    if(currentScope.variables[index] == null) parsingError("Null variable " + index);
    Node node = currentScope.variables[index].resolve();
    node.type = type;
    return node;
  }

  @Override
  public String toString() {
    return type.name + ":#" + index;
  }
}
