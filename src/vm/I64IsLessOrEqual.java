package vm;

public class I64IsLessOrEqual extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    booleanStack[stackPointer]
        = i64Stack[stackPointer] <= i64Stack[stackPointer + 1];
    if(log) typeStack[stackPointer] = ValueType.BOOLEAN;
    currentCommand++;
  }
}
