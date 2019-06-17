package export;

import parser.structure.Entity;

public class ChunkAddTab extends Chunk {
  @Override
  public String toString(Entity entity) {
    tabs += "  ";
    return "";
  }
}
