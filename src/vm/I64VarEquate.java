package vm;

import ast.Entity;
import base.ElException;

public class I64VarEquate extends VMCommand {
  int index;

  public I64VarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public I64VarEquate create(Entity entity) throws ElException {
    return new I64VarEquate(entity.getIndex());
  }
  
  @Override
  public void execute() {
    i64Stack[index + currentCall.paramPosition] = i64Stack[stackPointer];
    typeStack[index + currentCall.paramPosition] = TYPE_I64;
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
