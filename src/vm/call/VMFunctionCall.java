package vm.call;

import ast.exception.ElException;
import vm.VMBase;
import vm.VMFieldCommand;
import vm.values.VMValue;

public class VMFunctionCall extends VMBase {
  private final int returnPoint, paramPosition, deallocation;
  
  public VMFunctionCall(int paramPosition, int deallocation) {
    this.paramPosition = paramPosition;
    this.returnPoint = currentCommand + 1;
    this.deallocation = deallocation;
  }

  public void thisPush() throws ElException {
    stackPointer++;
    objectStack[stackPointer] = objectStack[paramPosition - 1];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
  }

  public VMValue thisField(int fieldIndex) throws ElException {
    return objectStack[paramPosition - 1].getField(fieldIndex);
  }

  public int varIndex(int index) {
    return index == VMFieldCommand.LAST ? stackPointer : index + paramPosition;
  }

  public void returnFromCall(int value) {
    stackPointer = stackPointer - deallocation;
    currentCommand = returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }
}
