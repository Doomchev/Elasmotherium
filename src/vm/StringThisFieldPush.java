package vm;

import base.ElException;
import static vm.VMBase.stringStack;

public class StringThisFieldPush extends VMFieldCommand {
  public StringThisFieldPush(int fieldIndex) {
    super(fieldIndex);
  }
  
  @Override
  public VMCommand create(int index) throws ElException {
    return new StringThisFieldPush(index);
  }
  
  @Override
  public void execute() throws ElException {
    stackPointer++;
    stringStack[stackPointer] = currentCall.thisField(fieldIndex).stringGet();
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
}
