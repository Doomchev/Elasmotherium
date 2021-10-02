package vm.i64;

import exception.ElException;
import vm.VMCommand;

public class I64IteratorNext extends VMCommand {
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = objectStack[stackPointer].i64Next();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
