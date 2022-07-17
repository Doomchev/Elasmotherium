package vm.i64.var;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64VarEquate extends VMCommand {
  private final int index;

  public I64VarEquate(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws EntityException, ElException {
    return new I64VarEquate(parameter.getValue().getIndex(), proLine, entity);
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
