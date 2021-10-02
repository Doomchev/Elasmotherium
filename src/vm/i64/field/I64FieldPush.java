package vm.i64.field;

import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64FieldPush extends VMFieldCommand {
  public I64FieldPush(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new I64FieldPush(fieldIndex, varIndex);
  }
  
  @Override
  public void execute() throws ElException {
    if(varIndex != LAST) stackPointer++;
    i64Stack[stackPointer] = objectStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).i64Get();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
