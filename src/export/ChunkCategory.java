package export;

import ast.Entity;

public class ChunkCategory extends Chunk {
  @Override
  public String toString(Entity entity) {
    return entity.getName();
  }
}
