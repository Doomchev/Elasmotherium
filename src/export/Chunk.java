package export;

import parser.structure.Entity;

public abstract class Chunk implements Cloneable {
  public static String tabs = "";
  public static Export export;
  
  public Chunk nextChunk = null;
  
  public abstract String toString(Entity entity);

  boolean appendChunk(Chunk chunk) {
    return false;
  }

  boolean appendTo(ChunkString chunk) {
    return false;
  }
}
