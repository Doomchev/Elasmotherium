package vm.string;

import exception.ElException;
import vm.VMCommand;
import vm.values.StringValue;

public class StringAddToList extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer -= 2;
    valueStack[stackPointer + 1].listGet().add(
        new StringValue(stringStack[stackPointer + 2]));
    currentCommand++;
  }
}
