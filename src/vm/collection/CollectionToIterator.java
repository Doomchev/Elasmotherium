package vm.collection;

import exception.ElException;
import vm.VMCommand;

public class CollectionToIterator extends VMCommand {
  @Override
  public void execute() throws ElException {
    valueStack[stackPointer] = valueStack[stackPointer].getIterator();
    currentCommand++;
  }
}
