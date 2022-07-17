package vm.i64.field;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldIncrement extends VMFieldCommand {
  public I64FieldIncrement(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new I64FieldIncrement(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public void execute() throws ElException {
    valueStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).increment();
    if(varIndex == LAST) stackPointer--;
    currentCommand++;
  }
}