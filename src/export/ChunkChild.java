package export;

import static parser.ParserBase.error;
import parser.structure.Entity;
import parser.structure.ID;

public class ChunkChild extends Chunk {
  private final ID id, postfix;

  public ChunkChild(ID id, ID postfix) {
    this.id = id;
    this.postfix = postfix;
  }
  
  @Override
  public String toString(Entity entity) {
    Entity child = entity.getChild(id);
    if(child == null) error(id.string + " child of " + entity.getName()
        + " is not found");
    return export.exportEntity(child, postfix);
  }
}
