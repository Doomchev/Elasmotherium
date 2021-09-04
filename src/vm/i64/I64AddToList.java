package vm.i64;

import base.ElException;
import vm.VMCommand;
import vm.values.I64Value;

public class I64AddToList extends VMCommand {
  public static class I64AddToListNoDelete extends VMCommand {
    @Override
    public void execute() throws ElException {
      stackPointer -= 1;
      objectStack[stackPointer].listGet().add(
          new I64Value(i64Stack[stackPointer + 1]));
      currentCommand++;
    }
  }
  
  @Override
  public void execute() throws ElException {
    stackPointer -= 2;
    objectStack[stackPointer + 1].listGet().add(
        new I64Value(i64Stack[stackPointer + 2]));
    currentCommand++;
  }
}
