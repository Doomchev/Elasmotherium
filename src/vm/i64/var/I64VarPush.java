package vm.i64.var;

import base.ElException;
import processor.ProParameter;
import vm.VMCommand;

public class I64VarPush extends VMCommand {
  private final int index;

  public I64VarPush(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new I64VarPush(parameter.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = i64Stack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
