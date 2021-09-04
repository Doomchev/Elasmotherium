package processor;

import ast.ID;
import base.ElException;
import vm.VMBase;

public class BlockLabelSet extends ProCommand {
  private final ID label;

  public BlockLabelSet(ID label) {
    this.label = label;
  }

  @Override
  public void execute() throws ElException {
    currentBlock.setLabelPosition(label, VMBase.currentCommand + 1);
    if(log) log("set label " + label);
  }
}
