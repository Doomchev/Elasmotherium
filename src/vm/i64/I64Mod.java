package vm.i64;

import static vm.VMBase.currentCommand;
import vm.VMCommand;

public class I64Mod extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    i64Stack[stackPointer] %= i64Stack[stackPointer + 1];
    currentCommand++;
  }
}
