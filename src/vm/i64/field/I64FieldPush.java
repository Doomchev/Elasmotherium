package vm.i64.field;

import base.ElException;
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
    int newStackPointer = stackPointer + (varIndex == LAST ? 0 : 1);
    i64Stack[newStackPointer] = objectStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).i64Get();
    if(log) typeStack[newStackPointer] = ValueType.I64;
    stackPointer = newStackPointer;
    currentCommand++;
  }
}
