package vm.collection;

import exception.ElException;
import vm.VMCommand;

public class ObjectSetAtIndex extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer -= 3;
    valueStack[stackPointer + 1].valueSet(
        (int) i64Stack[stackPointer + 2], valueStack[stackPointer + 3]);
    currentCommand++;
  }
}
