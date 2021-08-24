package vm;

import base.ElException;

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
    objectStack[stackPointer].fields[fieldIndex].increment();
    stackPointer--;
    currentCommand++;
  }
}