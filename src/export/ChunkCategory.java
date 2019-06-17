package export;

import parser.structure.Entity;

public class ChunkCategory extends Chunk {
  @Override
  public String toString(Entity entity) {
    return entity.getName();
  }
}
