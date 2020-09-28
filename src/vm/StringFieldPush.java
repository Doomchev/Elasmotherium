package vm;

public class StringFieldPush extends Command {
  int fieldIndex;

  public StringFieldPush(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public Command execute() {
    stringStack[stackPointer] = objStack[stackPointer].fields[fieldIndex]
        .stringGet();
    typeStack[stackPointer] = TYPE_STRING;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
