package vm.i64;

import vm.VMCommand;

public class I64IsEqual extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    booleanStack[stackPointer] = i64Stack[stackPointer]
        == i64Stack[stackPointer + 1];
    if(log) typeStack[stackPointer] = ValueType.BOOLEAN;
    currentCommand++;
  }
}
