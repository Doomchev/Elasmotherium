package vm.functions;

import base.ElException;
import vm.VMCommand;

public class Floor extends VMCommand {
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = (long) Math.floor(f64Stack[stackPointer]);
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
