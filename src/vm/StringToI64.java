package vm;

public class StringToI64 extends VMCommand {
  @Override
  public void execute() {
    i64Stack[stackPointer] = Long.parseLong(stringStack[stackPointer]);
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
}
