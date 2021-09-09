package vm.function;

import base.ElException;
import vm.VMCommand;

public class Exit extends VMCommand {
  @Override
  public void execute() throws ElException {
    if(initialStack != stackPointer)
      throw new ElException("Stack leak");
    System.exit(0);
  }
}
