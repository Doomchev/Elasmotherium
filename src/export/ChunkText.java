package export;

import parser.structure.Entity;

public class ChunkText extends Chunk {
  @Override
  public String toString(Entity entity) {
    return entity.toString();
  }
}
