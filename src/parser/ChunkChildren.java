package parser;

public class ChunkChildren extends Chunk {
  private final Category type;
  private final Chunk delimiter;

  public ChunkChildren(Category type, Chunk delimiter) {
    this.type = type;
    this.delimiter = delimiter;
  }
  
  @Override
  public String toString(Node node) {
    String str = "";
    for(Node childNode : node.children) {
      if(childNode.type == type || type == null) {
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
