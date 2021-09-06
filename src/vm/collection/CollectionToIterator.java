package vm.collection;

import base.ElException;
import static vm.VMBase.currentCommand;
import vm.VMCommand;

public class CollectionToIterator extends VMCommand {
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer] = objectStack[stackPointer].getIterator();
    currentCommand++;
  }
}
