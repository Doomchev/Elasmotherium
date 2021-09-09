package vm.i64.field;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldIncrement extends VMFieldCommand {
  public I64FieldIncrement(int fieldIndex) {
    super(fieldIndex);
  }

  @Override
  public VMCommand create(int index) throws ElException {
    return new I64FieldIncrement(index);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer].getField(fieldIndex).increment();
    stackPointer--;
    currentCommand++;
  }
}