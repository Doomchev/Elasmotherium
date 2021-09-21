package vm.string.field;

import base.ElException;
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
    int newStackPointer = stackPointer + (varIndex == LAST ? 0 : 1);
    stringStack[newStackPointer] = objectStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).stringGet();
    if(log) typeStack[newStackPointer] = ValueType.STRING;
    stackPointer = newStackPointer;
    currentCommand++;
  }
}
