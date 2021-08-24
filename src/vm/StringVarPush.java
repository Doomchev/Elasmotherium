package vm;

import base.ElException;
import processor.ProBase;
import processor.ProParameter;

public class StringVarPush extends VMCommand {
  int index;

  public StringVarPush(int index) {
    this.index = index;
  }

  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new StringVarPush(ProBase.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = stringStack[currentCall.paramPosition + index];
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
