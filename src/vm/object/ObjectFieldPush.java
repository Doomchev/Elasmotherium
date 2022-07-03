package vm.object;

import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class ObjectFieldPush extends VMFieldCommand {
  public ObjectFieldPush(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new ObjectFieldPush(fieldIndex, varIndex);
  }
  
  @Override
  public void execute() throws ElException {
    if(varIndex != LAST) stackPointer++;
    valueStack[stackPointer] = valueStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).objectGet();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
