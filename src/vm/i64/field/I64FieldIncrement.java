package vm.i64.field;

import ast.exception.ElException;
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
    objectStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).increment();
    if(varIndex == LAST) stackPointer--;
    currentCommand++;
  }
}