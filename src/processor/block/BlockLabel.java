package processor.block;

import ast.ID;
import base.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;
public class BlockLabel extends ProParameter {
  public final ID name;

  public BlockLabel(String name) {
    this.name = ID.get(name);
  }
  
  @Override
  public void addLabelCommand(VMCommand command) throws EntityException {
    currentBlock.addLabelCommand(name, command);
  }

  @Override
  public String toString() {
    return "BlockLabel(" + name.string + ")";
  }
}
