package vm.call;

import ast.exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64Return extends VMCommand {
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return new I64Return();
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    currentCall.returnFromCall(1);
    i64Stack[stackPointer] = i64Stack[pos];
    if(log) typeStack[stackPointer] = ValueType.I64;
  }
}
