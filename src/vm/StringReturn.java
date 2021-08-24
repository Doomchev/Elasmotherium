package vm;

import base.ElException;
import processor.ProParameter;
import static vm.VMBase.stackPointer;

public class StringReturn extends Return {
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new StringReturn();
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    returnFromCall(1);
    stringStack[stackPointer] = stringStack[pos];
    if(log) typeStack[stackPointer] = ValueType.STRING;
  }
}
