package vm;

public class I64FieldPush extends Command {
  int fieldIndex;

  public I64FieldPush(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public Command execute() {
    i64Stack[stackPointer] = objStack[stackPointer].fields[fieldIndex].i64Get();
    typeStack[stackPointer] = TYPE_I64;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
