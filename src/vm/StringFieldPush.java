package vm;

import base.ElException;

public class StringFieldPush extends Command {
  int fieldIndex;

  public StringFieldPush(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public void execute() throws ElException {
    stringStack[stackPointer] = objectStack[stackPointer].fields[fieldIndex]
        .stringGet();
    typeStack[stackPointer] = TYPE_STRING;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
