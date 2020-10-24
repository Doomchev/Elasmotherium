package vm;

public class StringFieldEquate extends Command {
  int stackIndex, fieldIndex;

  public StringFieldEquate(int stackIndex, int fieldIndex) {
    this.stackIndex = stackIndex;
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public void execute() {
    objectStack[stackIndex + currentCall.paramPosition].fields[fieldIndex]
        .stringSet(stringStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + stackIndex + " " + fieldIndex;
  }
}
