package vm.function;

import exception.ElException;
import exception.ElException.MethodException;
import vm.VMCommand;

public class Exit extends VMCommand {
  @Override
  public void execute() throws ElException {
    if(initialStack != stackPointer)
      throw new MethodException(this, "execute", "Stack leak");
    currentCommand = -1;
  }
}
