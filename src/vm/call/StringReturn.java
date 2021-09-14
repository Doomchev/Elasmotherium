package vm.call;

import base.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringReturn extends VMCommand {
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new StringReturn();
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    currentCall.returnFromCall(1);
    stringStack[stackPointer] = stringStack[pos];
    if(log) typeStack[stackPointer] = ValueType.STRING;
  }
}
