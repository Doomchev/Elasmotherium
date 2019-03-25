package export;

import parser.Category;
import parser.structure.Node;

public class ChunkExists extends Chunk {
  Category childNodeCategory;
  Chunk firstChunk;

  public ChunkExists(Category childNodeCategory, Chunk firstChunk) {
    this.childNodeCategory = childNodeCategory;
    this.firstChunk = firstChunk;
  }
  
  @Override
  public String toString(Node node) {
    if(node.hasChild(childNodeCategory)) {
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
