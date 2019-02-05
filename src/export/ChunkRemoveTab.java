package export;

import parser.structure.Node;

public class ChunkRemoveTab extends Chunk {
  @Override
  public String toString(Node node) {
    tabs = tabs.substring(2);
    return "";
  }
}
