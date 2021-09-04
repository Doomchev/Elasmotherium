package vm.f64;

import base.ElException;
import processor.ProParameter;
import vm.VMCommand;

public class F64VarPush extends VMCommand {
  private final int index;

  public F64VarPush(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
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
