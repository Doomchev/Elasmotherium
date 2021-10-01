package vm.collection;

import ast.exception.ElException;
import vm.VMCommand;

public class CollectionToIterator extends VMCommand {
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer] = objectStack[stackPointer].getIterator();
    currentCommand++;
  }
}
