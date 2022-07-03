package vm.collection;

import exception.ElException;
import vm.VMCommand;

public class IteratorHasNext extends VMCommand {
  @Override
  public void execute() throws ElException {
    booleanStack[stackPointer] = valueStack[stackPointer].hasNext();
    if(log) typeStack[stackPointer] = ValueType.BOOLEAN;
    currentCommand++;
  }
}
