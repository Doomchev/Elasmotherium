package vm.collection;

import static base.Base.log;
import base.ElException;
import vm.VMBase;
import static vm.VMBase.currentCommand;
import vm.VMCommand;
import vm.values.I64ArrayValue;

public class I64ArrayCreate1 extends VMCommand {
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer] = new I64ArrayValue((int) i64Stack[stackPointer]);
    if(log) typeStack[stackPointer] = VMBase.ValueType.OBJECT;
    currentCommand++;
  }
}
