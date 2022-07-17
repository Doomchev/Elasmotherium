package vm.i64.field;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldPush extends VMFieldCommand {
  public I64FieldPush(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new I64FieldPush(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public void execute() throws ElException {
    if(varIndex != LAST) stackPointer++;
    i64Stack[stackPointer] = valueStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).i64Get();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
