package vm;

public class I64FieldEquate extends Command {
  int stackIndex, fieldIndex;

  public I64FieldEquate(int stackIndex, int fieldIndex) {
    this.stackIndex = stackIndex;
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public Command execute() {
    objStack[stackIndex + currentCall.paramPosition]
        .i64Set(i64Stack[stackPointer]);
    stackPointer--;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + stackIndex + " " + fieldIndex;
  }
}
