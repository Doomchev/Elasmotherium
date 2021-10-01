package vm.i64.var;

import ast.exception.ElException;
import ast.exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64VarPush extends VMCommand {
  private final int index;

  public I64VarPush(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
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
