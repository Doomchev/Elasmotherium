package vm.i64.field;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldEquate extends VMFieldCommand {
  public I64FieldEquate(int fieldIndex) {
    super(fieldIndex);
  }
  
  @Override
  public VMCommand create(int index) throws ElException {
    return new I64FieldEquate(index);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer - 1].getField(fieldIndex)
        .i64Set(i64Stack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
