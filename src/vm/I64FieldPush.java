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
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
