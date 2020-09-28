package export;

import ast.Entity;

public class ChunkText extends Chunk {
  @Override
  public String toString(Entity entity) {
    return entity.toString();
  }
}
