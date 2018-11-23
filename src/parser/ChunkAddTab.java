package parser;

public class ChunkAddTab extends Chunk {
  @Override
  public String toString(Node node) {
    tabs += "  ";
    return "";
  }
}
