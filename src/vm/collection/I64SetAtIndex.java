package vm.collection;

import base.ElException;
import vm.VMCommand;

public class I64SetAtIndex extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer -= 3;
    objectStack[stackPointer + 1].i64Set(
        (int) i64Stack[stackPointer + 2], i64Stack[stackPointer + 3]);
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
