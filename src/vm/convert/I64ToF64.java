package vm.convert;

import vm.VMCommand;

public class I64ToF64 extends VMCommand {
  @Override
  public void execute() {
    f64Stack[stackPointer] = (double) i64Stack[stackPointer];
    if(log) typeStack[stackPointer] = ValueType.F64;
    currentCommand++;
  }
}
