package vm;

import ast.Entity;
import base.ElException;

public class I64VarPush extends VMCommand {
  int index;

  public I64VarPush(int index) {
    this.index = index;
  }
  
  @Override
  public I64VarPush create(Entity entity) throws ElException {
    return new I64VarPush(entity.getIndex());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = i64Stack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
