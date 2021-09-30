package vm.object;

import base.ElException;
import base.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectVarPush extends VMCommand {
  private final int index;

  public ObjectVarPush(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
    return new ObjectVarPush(parameter.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    objectStack[stackPointer] = objectStack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
