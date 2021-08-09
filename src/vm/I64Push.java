package vm;

import ast.Entity;
import base.ElException;

public class I64Push extends VMCommand {
  long value;

  public I64Push(long value) {
    this.value = value;
  }
  
  @Override
  public I64Push create(Entity entity) throws ElException {
    return new I64Push(Integer.parseInt(entity.getStringValue()));
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = value;
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + value;
  }
}
