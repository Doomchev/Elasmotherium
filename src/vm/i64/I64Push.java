package vm.i64;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64Push extends VMCommand {
  long value;

  public I64Push(long value) {
    super();
    this.value = value;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws EntityException, ElException {
    return new I64Push(Integer.parseInt(parameter.getValue().getStringValue()));
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
