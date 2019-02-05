package export;

import parser.structure.Node;

public class ChunkChildAtIndex extends Chunk {
  private final int index;

  public ChunkChildAtIndex(int index) {
    this.index = index;
  }
  
  @Override
  public String toString(Node node) {
    return export.exportNode(node.children.get(index));
  }
}
