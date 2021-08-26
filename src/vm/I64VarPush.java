package vm;

import base.ElException;
import processor.ProBase;
import processor.ProParameter;

public class I64VarPush extends VMCommand {
  private final int index;

  public I64VarPush(int index) {
    this.index = index;
  }
  
  @Override
  public I64VarPush create(ProParameter parameter) throws ElException {
    return new I64VarPush(ProBase.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = i64Stack[currentCall.paramPosition + index];
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
