package export;

import parser.structure.Entity;

public class ChunkChildAtIndex extends Chunk {
  private final int index;

  public ChunkChildAtIndex(int index) {
    this.index = index;
  }
  
  @Override
  public String toString(Entity entity) {
    return export.exportEntity(entity.getChild(index));
  }
}
