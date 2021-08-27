package vm;

import vm.values.VMValue;

public class VMFunctionCall extends VMBase {
  private final int returnPoint, paramPosition, deallocation;
  
  public VMFunctionCall(int paramPosition, int deallocation) {
    this.paramPosition = paramPosition;
    this.returnPoint = currentCommand + 1;
    this.deallocation = deallocation;
  }

  public VMValue thisField(int fieldIndex) {
    return objectStack[paramPosition - 1].fields[fieldIndex];
  }

  public int varIndex(int index) {
    return index + paramPosition;
  }

  public void returnFromCall(int value) {
    stackPointer = stackPointer - deallocation;
    currentCommand = returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }
}
