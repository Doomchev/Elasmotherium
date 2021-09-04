package processor;

import ast.Entity;
import ast.ID;
import base.ElException;

public class BlockVariable extends ProParameter {
  public final ID name;

  public BlockVariable(String name) {
    this.name = ID.get(name);
  }

  @Override
  public Entity getValue() throws ElException {
    return currentBlock.getVariable(name);
  }

  @Override
  public String toString() {
    return "BlockVariable(" + name.string + ")";
  }
}
