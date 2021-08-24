package vm;

import base.ElException;
import processor.ProParameter;

public class Return extends VMCommand {
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new Return();
  }
  
  @Override
  public void execute() {
    returnFromCall(0);
  }

  protected void returnFromCall(int value) {
    stackPointer = stackPointer - currentCall.deallocation;
    currentCommand = currentCall.returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }
}
