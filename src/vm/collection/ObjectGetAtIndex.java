package vm.collection;

import exception.ElException;
import vm.VMCommand;

public class ObjectGetAtIndex extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer--;
    objectStack[stackPointer] = objectStack[stackPointer]
        .objectGet((int) i64Stack[stackPointer + 1]);
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
}
