package vm.i64.field;

import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldIncrement extends VMFieldCommand {
  public I64FieldIncrement(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new I64FieldIncrement(fieldIndex, varIndex);
  }
  
  @Override
  public void execute() throws ElException {
    valueStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).increment();
    if(varIndex == LAST) stackPointer--;
    currentCommand++;
  }
}