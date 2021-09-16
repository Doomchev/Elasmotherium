package vm.i64;

import static vm.VMBase.currentCommand;
import vm.VMCommand;

public class I64Negative extends VMCommand {
  @Override
  public void execute() {
    i64Stack[stackPointer] = -i64Stack[stackPointer];
    currentCommand++;
  }
}
