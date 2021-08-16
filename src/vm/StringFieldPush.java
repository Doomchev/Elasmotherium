package vm;

import base.ElException;

public class StringFieldPush extends VMCommand {
  int fieldIndex;

  public StringFieldPush(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public void execute() throws ElException {
    stringStack[stackPointer] = objectStack[stackPointer].fields[fieldIndex]
        .stringGet();
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
