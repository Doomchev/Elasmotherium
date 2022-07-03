package vm.f64.var;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class F64VarEquate extends VMCommand {
  private final int index;

  public F64VarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws EntityException, ElException {
    return new F64VarEquate(parameter.getValue().getIndex());
  }
  
  @Override
  public void execute() {
    f64Stack[currentCall.varIndex(index)] = f64Stack[stackPointer];
    if(log) typeStack[currentCall.varIndex(index)] = ValueType.F64;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
