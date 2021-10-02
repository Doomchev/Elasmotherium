package vm.string.field;

import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringFieldPush extends VMFieldCommand {
  public StringFieldPush(int fieldIndex, int varIndex) {
    super(fieldIndex, varIndex);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex) {
    return new StringFieldPush(fieldIndex, varIndex);
  }
  
  @Override
  public void execute() throws ElException {
    if(varIndex != LAST) stackPointer++;
    stringStack[stackPointer] = objectStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).stringGet();
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
}
