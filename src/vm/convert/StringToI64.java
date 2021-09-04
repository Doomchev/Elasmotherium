package vm.convert;

import vm.VMCommand;

public class StringToI64 extends VMCommand {
  @Override
  public void execute() {
    i64Stack[stackPointer] = Long.parseLong(stringStack[stackPointer]);
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
