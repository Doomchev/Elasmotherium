package vm.object;

import base.ElException;
import vm.VMCommand;

public class ObjectThisPush extends VMCommand {
  @Override
  public void execute() throws ElException {
    currentCall.thisPush();
    currentCommand++;
  }
}
