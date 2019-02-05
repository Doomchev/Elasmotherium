package export;

import parser.structure.Node;

public class ChunkString extends Chunk {
  private String string;

  public ChunkString(String str) {
    this.string = str;
  }

  @Override
  boolean appendChunk(Chunk chunk) {
    return chunk.appendTo(this);
  }

  @Override
  public boolean appendTo(ChunkString chunk) {
    chunk.string += string;
    return true;
  }

  @Override
  public String toString(Node node) {
    return string;
  }
}
