package export;

import parser.structure.Node;

public abstract class Chunk implements Cloneable {
  public static String tabs = "";
  public static Export export;
  
  public Chunk nextChunk = null;
  
  public abstract String toString(Node node);

  boolean appendChunk(Chunk chunk) {
    return false;
  }

  boolean appendTo(ChunkString chunk) {
    return false;
  }
}
