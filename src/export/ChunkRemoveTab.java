package export;

import parser.structure.Entity;

public class ChunkRemoveTab extends Chunk {
  @Override
  public String toString(Entity entity) {
    tabs = tabs.substring(2);
    return "";
  }
}
