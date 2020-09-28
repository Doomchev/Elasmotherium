package export;

import ast.Entity;

public class ChunkTabs extends Chunk {
  @Override
  public String toString(Entity entity) {
    return tabs;
  }
}
