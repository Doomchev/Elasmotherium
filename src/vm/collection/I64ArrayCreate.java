package vm.collection;

import exception.ElException;
import vm.VMBase;
import vm.VMCommand;
import vm.values.I64ArrayValue;

public class I64ArrayCreate extends VMCommand {
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer] = new I64ArrayValue((int) i64Stack[stackPointer]);
    if(log) typeStack[stackPointer] = VMBase.ValueType.OBJECT;
    currentCommand++;
  }
}
