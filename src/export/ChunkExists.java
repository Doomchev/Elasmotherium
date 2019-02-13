package export;

import parser.Category;
import parser.structure.Node;

public class ChunkExists extends Chunk {
  Category childNodeType;
  Chunk firstChunk;

  public ChunkExists(Category childNodeType, Chunk firstChunk) {
    this.childNodeType = childNodeType;
    this.firstChunk = firstChunk;
  }
  
  @Override
  public String toString(Node node) {
    if(node.hasChild(childNodeType)) {
      String str = "";
      Chunk chunk = firstChunk;
      while(chunk != null) {
        str += chunk.toString(node);
        chunk = chunk.nextChunk;
      }
      return str;
    }
    return "";
  }
}
