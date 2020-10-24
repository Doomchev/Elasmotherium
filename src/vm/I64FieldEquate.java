package vm;

public class I64FieldEquate extends Command {
  int stackIndex, fieldIndex;

  public I64FieldEquate(int stackIndex, int fieldIndex) {
    this.stackIndex = stackIndex;
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public void execute() {
    objectStack[stackIndex + currentCall.paramPosition].fields[fieldIndex]
        .i64Set(i64Stack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + stackIndex + " " + fieldIndex;
  }
}
