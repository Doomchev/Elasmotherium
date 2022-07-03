package vm.collection;

import exception.ElException;
import vm.VMCommand;

public class I64GetAtIndex extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer--;
    i64Stack[stackPointer] = valueStack[stackPointer]
        .i64Get((int) i64Stack[stackPointer + 1]);
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
