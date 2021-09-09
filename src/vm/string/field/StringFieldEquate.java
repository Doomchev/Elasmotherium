package vm.string.field;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringFieldEquate extends VMFieldCommand {
  public StringFieldEquate(int fieldIndex) {
    super(fieldIndex);
  }

  @Override
  public VMCommand create(int index) throws ElException {
    return new StringFieldEquate(index);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer].getField(fieldIndex)
        .stringSet(stringStack[stackPointer]);
    currentCommand++;
  }
}
