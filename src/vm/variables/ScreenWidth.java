package vm.variables;

import base.ElException;
import vm.VMCommand;

public class ScreenWidth extends VMCommand {
  @Override
  public void execute() throws ElException {
    stackPointer++;
    i64Stack[stackPointer] = frame.getWidth();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
