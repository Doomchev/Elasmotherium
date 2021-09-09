package vm.i64.var;

import base.ElException;
import processor.ProParameter;
import vm.VMCommand;

public class I64VarEquate extends VMCommand {
  private final int index;

  public I64VarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new I64VarEquate(parameter.getValue().getIndex());
  }
  
  @Override
  public void execute() {
    i64Stack[currentCall.varIndex(index)] = i64Stack[stackPointer];
    if(log) typeStack[currentCall.varIndex(index)] = ValueType.I64;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
