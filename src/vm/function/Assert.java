package vm.function;

import base.ElException;
import static vm.VMBase.currentCommand;
import vm.VMCommand;

public class Assert extends VMCommand {
  @Override
  public void execute() throws ElException {
    if(!booleanStack[stackPointer]) throw new ElException(this, "test failed");
    stackPointer--;
    currentCommand++;
  }
}
