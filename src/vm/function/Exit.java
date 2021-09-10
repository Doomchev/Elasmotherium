package vm.function;

import base.ElException;
import base.ElException.MethodException;
import vm.VMCommand;

public class Exit extends VMCommand {
  @Override
  public void execute() throws ElException {
    if(initialStack != stackPointer)
      throw new MethodException(this, "execute", "Stack leak");
    System.exit(0);
  }
}
