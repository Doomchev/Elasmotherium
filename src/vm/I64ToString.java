package vm;

public class I64ToString extends VMCommand {
  @Override
  public void execute() {
    stringStack[stackPointer] = String.valueOf(i64Stack[stackPointer]);
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
}
