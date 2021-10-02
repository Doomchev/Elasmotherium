package vm.object;

import exception.ElException;
import vm.VMCommand;

public class ObjectThisPush extends VMCommand {
  @Override
  public void execute() throws ElException {
    currentCall.thisPush();
    currentCommand++;
  }
}
