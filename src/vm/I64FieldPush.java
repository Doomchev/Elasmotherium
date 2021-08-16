package vm;

import base.ElException;

public class I64FieldPush extends VMCommand {
  int fieldIndex;

  public I64FieldPush(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = objectStack[stackPointer].fields[fieldIndex].i64Get();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
