package parser.structure;

import parser.Category;

public class StoredNodeValue extends Node {
  private final int index;

  public StoredNodeValue(int index, Category category) {
    super(null);
    this.index = index;
    this.category = category;
  }

  @Override
  public Node resolve() {
    if(currentParserScope.variables[index] == null) error("Null variable " + index);
    Node storedNode = currentParserScope.variables[index].resolve();
    Node node = children.isEmpty() ? storedNode : super.resolve();
    node.category = category;
    node.caption = storedNode.caption;
    return node;
  }

  @Override
  public String toString() {
    return category.name + ":#" + index;
  }
}
