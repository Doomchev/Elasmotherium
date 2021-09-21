package vm.string.field;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringFieldEquate extends VMFieldCommand {
  public StringFieldEquate(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new StringFieldEquate(fieldIndex, varIndex);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[currentCall.varIndex(varIndex)].getField(fieldIndex)
        .stringSet(stringStack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
