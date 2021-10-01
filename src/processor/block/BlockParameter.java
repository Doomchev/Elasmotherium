package processor.block;

import ast.Entity;
import ast.ID;
import ast.exception.EntityException;
import processor.parameter.ProParameter;

public class BlockParameter extends ProParameter {
  private final ID name;

  public BlockParameter(String name) {
    this.name = ID.get(name);
  }

  @Override
  public Entity getValue() throws EntityException {
    return currentBlock.getBlockParameter(name);
  }

  @Override
  public String toString() {
    return "BlockParameter(" + name.string + ")";
  }
}
