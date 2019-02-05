package export;

import parser.Category;
import parser.structure.Node;

public class ChunkChild extends Chunk {
  private final Category type;

  public ChunkChild(Category type) {
    this.type = type;
  }
  
  @Override
  public String toString(Node node) {
    for(Node childNode : node.children) {
      if(childNode.type == type) return export.exportNode(childNode);
    }
    return node.caption;
  }
}
