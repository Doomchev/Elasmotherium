package vm.collection;

import ast.exception.ElException;
import vm.VMCommand;

public class IteratorHasNext extends VMCommand {
  @Override
  public void execute() throws ElException {
    booleanStack[stackPointer] = objectStack[stackPointer].hasNext();
    if(log) typeStack[stackPointer] = ValueType.BOOLEAN;
    currentCommand++;
  }
}
