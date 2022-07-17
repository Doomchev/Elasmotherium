package vm.call;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringReturn extends VMCommand {
  public StringReturn(int proLine, Entity entity) {
    super(proLine, entity);
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new StringReturn(proLine, entity);
  }
  
  @Override
  public void execute() {
    int pos = stackPointer;
    currentCall.returnFromCall(1);
    stringStack[stackPointer] = stringStack[pos];
    if(log) typeStack[stackPointer] = ValueType.STRING;
  }
}
