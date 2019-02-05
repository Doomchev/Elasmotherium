package export;

import parser.structure.Node;

public class ChunkTabs extends Chunk {
  @Override
  public String toString(Node node) {
    return tabs;
  }
}
