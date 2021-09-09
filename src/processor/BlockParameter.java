package processor;

import ast.Entity;
import ast.ID;
import base.ElException;

public class BlockParameter extends ProParameter {
  private final ID name;

  public BlockParameter(String name) {
    this.name = ID.get(name);
  }

  @Override
  public Entity getValue() throws ElException {
    return currentBlock.getBlockParameter(name);
  }

  @Override
  public String toString() {
    return "BlockParameter(" + name.string + ")";
  }
}
