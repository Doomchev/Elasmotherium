package vm.object;

import base.ElException;
import static vm.VMBase.currentCommand;
import vm.VMCommand;

public class ObjectThisPush extends VMCommand {
  @Override
  public void execute() throws ElException {
    currentCall.thisPush();
    currentCommand++;
  }
}
