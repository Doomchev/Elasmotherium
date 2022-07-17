package vm.i64;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64Push extends VMCommand {
  long value;

  public I64Push(long value, int proLine, Entity entity) {
    super(proLine, entity);
    this.value = value;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws EntityException, ElException {
    return new I64Push(Integer.parseInt(parameter.getValue().getStringValue())
        , proLine, entity);
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = value;
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + value;
  }
}
