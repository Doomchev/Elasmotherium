package vm.f64.var;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class F64VarPush extends VMCommand {
  private final int index;

  public F64VarPush(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new F64VarPush(parameter.getIndex(), proLine, entity);
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
