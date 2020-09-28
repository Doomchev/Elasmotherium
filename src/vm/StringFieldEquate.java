package vm;

public class StringFieldEquate extends Command {
  int stackIndex, fieldIndex;

  public StringFieldEquate(int stackIndex, int fieldIndex) {
    this.stackIndex = stackIndex;
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public Command execute() {
    objStack[stackIndex + currentCall.paramPosition]
        .stringSet(stringStack[stackPointer]);
    stackPointer--;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + stackIndex + " " + fieldIndex;
  }
}
