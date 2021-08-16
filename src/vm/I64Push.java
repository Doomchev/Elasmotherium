package vm;

import base.ElException;
import processor.ProParameter;

public class I64Push extends VMCommand {
  long value;

  public I64Push(long value) {
    this.value = value;
  }
  
  @Override
  public I64Push create(ProParameter parameter) throws ElException {
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
