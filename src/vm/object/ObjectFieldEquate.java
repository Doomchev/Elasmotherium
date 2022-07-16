package vm.object;

import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;
import vm.i64.field.I64FieldEquate;

public class ObjectFieldEquate extends VMFieldCommand {
  public ObjectFieldEquate(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }

  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new ObjectFieldEquate(fieldIndex, varIndex);
  }

  @Override
  public void execute() throws ElException {
    valueStack[currentCall.varIndex(varIndex)].setField(
        fieldIndex, valueStack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
