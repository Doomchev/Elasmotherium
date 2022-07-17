package vm.call;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectReturn extends VMCommand {
  public ObjectReturn(int proLine, Entity entity) {
    super(proLine, entity);
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new ObjectReturn(proLine, entity);
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    currentCall.returnFromCall(1);
    valueStack[stackPointer] = valueStack[pos];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
  }
}
