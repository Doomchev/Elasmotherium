package vm.call;

import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectReturn extends VMCommand {
  public ObjectReturn() {
    super();
  }

  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new ObjectReturn();
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    currentCall.returnFromCall(1);
    valueStack[stackPointer] = valueStack[pos];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
  }
}
