package export;

import parser.Category;
import parser.structure.Node;

public class ChunkChildren extends Chunk {
  private final Category category;
  private final Chunk delimiter;

  public ChunkChildren(Category category, Chunk delimiter) {
    this.category = category;
    this.delimiter = delimiter;
  }
  
  @Override
  public String toString(Node node) {
    String str = "";
    for(Node childNode : node.children) {
      if(childNode.category == category || category == null) {
        if(!str.isEmpty()) {
          Chunk chunk = delimiter;
          while(chunk != null) {
            str += chunk.toString(node);
            chunk = chunk.nextChunk;
          }
        }
        str += export.exportNode(childNode);
      }
    }
    return str;
  }
}
