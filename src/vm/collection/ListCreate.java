package vm.collection;

import exception.ElException;
import vm.VMCommand;
import vm.values.ListValue;

public class ListCreate extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer++;
    valueStack[stackPointer] = new ListValue();
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
}
