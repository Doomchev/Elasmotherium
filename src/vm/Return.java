package vm;

import ast.ID;
import static base.Base.currentAllocation;
import base.ElException;
import processor.ProParameter;

public class Return extends VMCommand {
  int deallocate;

  public Return(int deallocate) {
    this.deallocate = deallocate;
  }

  @Override
  public Return create(ProParameter parameter) throws ElException {
    return new Return(currentAllocation);
  }
  
  @Override
  public void execute() {
    stackPointer = stackPointer - deallocate;
    currentCommand = currentCall.returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }  
}
