package vm.i64;

import vm.VMCommand;

public class I64Divide extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    i64Stack[stackPointer] /= i64Stack[stackPointer + 1];
    currentCommand++;
  }
}
