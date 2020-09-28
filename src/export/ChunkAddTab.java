package export;

import ast.Entity;

public class ChunkAddTab extends Chunk {
  @Override
  public String toString(Entity entity) {
    tabs += "  ";
    return "";
  }
}
