package vm;

import base.ElException;
import processor.ProParameter;

public class I64Return extends VMCommand {
  int deallocate;

  public I64Return(int deallocate) {
    this.deallocate = deallocate;
  }

  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new I64Return(currentAllocation + currentFunction.parameters.size());
  }
  
  @Override
  public void execute() {
    int newStackPointer = stackPointer - deallocate;
    i64Stack[newStackPointer] = i64Stack[stackPointer];
    if(log) typeStack[currentCall.paramPosition] = ValueType.I64;
    stackPointer = newStackPointer;
    currentCommand = currentCall.returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }

  @Override
  public String toString() {
    return "I64Return(" + deallocate + ')';
  }
}
