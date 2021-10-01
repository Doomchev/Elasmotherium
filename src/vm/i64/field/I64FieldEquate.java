package vm.i64.field;

import ast.exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldEquate extends VMFieldCommand {
  public I64FieldEquate(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new I64FieldEquate(fieldIndex, varIndex);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[currentCall.varIndex(varIndex)].getField(fieldIndex)
        .i64Set(i64Stack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
