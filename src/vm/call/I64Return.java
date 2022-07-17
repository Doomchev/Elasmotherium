package vm.call;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64Return extends VMCommand {
  public I64Return(int proLine, Entity entity) {
    super(proLine, entity);
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new I64Return(proLine, entity);
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    currentCall.returnFromCall(1);
    i64Stack[stackPointer] = i64Stack[pos];
    if(log) typeStack[stackPointer] = ValueType.I64;
  }
}
