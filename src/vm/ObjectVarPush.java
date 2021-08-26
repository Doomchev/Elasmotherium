package vm;

import base.ElException;
import processor.ProBase;
import processor.ProParameter;

public class ObjectVarPush extends VMCommand {
  private final int index;

  public ObjectVarPush(int index) {
    this.index = index;
  }
  
  @Override
  public ObjectVarPush create(ProParameter parameter) throws ElException {
    return new ObjectVarPush(ProBase.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    objectStack[stackPointer] = objectStack[currentCall.paramPosition + index];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
