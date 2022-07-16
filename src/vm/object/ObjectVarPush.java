package vm.object;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectVarPush extends VMCommand {
  private final int index;

  public ObjectVarPush(int index) {
    super();
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
    valueStack[stackPointer] = valueStack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
