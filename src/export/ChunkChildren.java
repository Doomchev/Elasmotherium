package export;

import ast.Entity;
import ast.ID;

public class ChunkChildren extends Chunk {
  private final Chunk delimiter;
  private final ID postfix;

  public ChunkChildren(Chunk delimiter, ID postfix) {
    this.delimiter = delimiter;
    this.postfix = postfix;
  }
  
  @Override
  public String toString(Entity entity) {
    String str = "";
    boolean first = true;
    for(Entity child : entity.getChildren()) {
      if(child.hasFlag(ID.nativeID)) continue;
      if(!first) {
        Chunk chunk = delimiter;
        while(chunk != null) {
          str += chunk.toString(child);
          chunk = chunk.nextChunk;
        }
      }
      str += export.exportEntity(child, postfix);
      first = false;
    }
    return str;
  }
}
