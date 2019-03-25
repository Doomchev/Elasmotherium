package export;

import parser.Category;
import parser.structure.Node;

public class ChunkChild extends Chunk {
  private final Category category;

  public ChunkChild(Category category) {
    this.category = category;
  }
  
  @Override
  public String toString(Node node) {
    for(Node childNode : node.children) {
      if(childNode.category == category) return export.exportNode(childNode);
    }
    return node.caption;
  }
}
