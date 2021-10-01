package processor.block;

import ast.ID;
import ast.exception.ElException;
import ast.exception.EntityException;
import ast.exception.NotFound;
import java.util.logging.Level;
import java.util.logging.Logger;
import processor.ProCommand;
import vm.VMBase;

public class BlockLabelSet extends ProCommand {
  private final ID label;

  public BlockLabelSet(ID label) {
    this.label = label;
  }

  @Override
  public void execute() throws EntityException {
    currentBlock.setLabelPosition(label, VMBase.currentCommand + 1);
    if(log) log("set label " + label);
  }
}
