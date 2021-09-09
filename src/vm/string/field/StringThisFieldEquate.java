package vm.string.field;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringThisFieldEquate extends VMFieldCommand {
  public StringThisFieldEquate(int fieldIndex) {
    super(fieldIndex);
  }

  @Override
  public VMCommand create(int index) throws ElException {
    return new StringThisFieldEquate(index);
  }
  
  @Override
  public void execute() throws ElException {
    currentCall.thisField(fieldIndex).stringSet(stringStack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
