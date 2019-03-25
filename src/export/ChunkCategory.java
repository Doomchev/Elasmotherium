package export;

import parser.structure.Node;

public class ChunkCategory extends Chunk {
  @Override
  public String toString(Node node) {
    return node.category.name;
  }
}
