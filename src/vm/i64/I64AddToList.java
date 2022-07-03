package vm.i64;

import exception.ElException;
import vm.VMCommand;
import vm.values.I64Value;

public class I64AddToList extends VMCommand {
  public static class I64AddToListNoDelete extends VMCommand {
    @Override
    public void execute() throws ElException {
      stackPointer -= 1;
      valueStack[stackPointer].listGet().add(
          new I64Value(i64Stack[stackPointer + 1]));
      currentCommand++;
    }
  }
  
  @Override
  public void execute() throws ElException {
    stackPointer -= 2;
    valueStack[stackPointer + 1].listGet().add(
        new I64Value(i64Stack[stackPointer + 2]));
    currentCommand++;
  }
}
