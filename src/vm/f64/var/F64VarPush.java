package vm.f64.var;

import base.ElException;
import base.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class F64VarPush extends VMCommand {
  private final int index;

  public F64VarPush(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
    return new F64VarPush(parameter.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    f64Stack[stackPointer] = f64Stack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.F64;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
