package vm.i64.var;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64VarPush extends VMCommand {
  private final int index;

  public I64VarPush(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException, EntityException {
    return new I64VarPush(parameter.getIndex(), proLine, entity);
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
