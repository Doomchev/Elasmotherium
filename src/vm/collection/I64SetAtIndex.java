package vm.collection;

import exception.ElException;
import vm.VMCommand;

public class I64SetAtIndex extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer -= 3;
    valueStack[stackPointer + 1].i64Set(
        (int) i64Stack[stackPointer + 2], i64Stack[stackPointer + 3]);
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
