package parser;

public class ChunkType extends Chunk {
  @Override
  public String toString(Node node) {
    return node.type.name;
  }
}
