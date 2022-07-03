package vm.string.field;

import exception.ElException;
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
    valueStack[currentCall.varIndex(varIndex)].getField(fieldIndex)
        .stringSet(stringStack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
