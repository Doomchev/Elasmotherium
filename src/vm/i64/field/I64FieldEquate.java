package vm.i64.field;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldEquate extends VMFieldCommand {
  public I64FieldEquate(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new I64FieldEquate(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public void execute() throws ElException {
    valueStack[currentCall.varIndex(varIndex)].getField(fieldIndex)
        .i64Set(i64Stack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
