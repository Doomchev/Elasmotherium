package processor;

import ast.ID;
import base.ElException;
import vm.VMCommand;
public class BlockLabel extends ProParameter {
  public final ID name;

  public BlockLabel(String name) {
    this.name = ID.get(name);
  }
  
  @Override
  public void addLabelCommand(VMCommand command) throws ElException {
    currentBlock.addLabelCommand(name, command);
  }

  @Override
  public String toString() {
    return "BlockLabel(" + name.string + ")";
  }
}
