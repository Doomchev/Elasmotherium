package export;

import ast.Entity;
import ast.ID;

public class ChunkExists extends Chunk {
  ID id;
  Chunk firstChunk;
  boolean condition;

  public ChunkExists(ID id, Chunk firstChunk, boolean condition) {
    this.id = id;
    this.firstChunk = firstChunk;
    this.condition = condition;
  }
  
  @Override
  public String toString(Entity entity) {
    if(entity.hasChild(id) == condition) {
      String str = "";
      Chunk chunk = firstChunk;
      while(chunk != null) {
        str += chunk.toString(entity);
        chunk = chunk.nextChunk;
      }
      return str;
    }
    return "";
  }
}
