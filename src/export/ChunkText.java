package export;

import parser.structure.Node;

public class ChunkText extends Chunk {
  @Override
  public String toString(Node node) {
    return node.caption;
  }
}
