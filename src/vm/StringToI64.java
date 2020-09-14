package vm;

public class StringToI64 extends Command {
  @Override
  public Command execute() {
    i64Stack[stackPointer] = Long.parseLong(stringStack[stackPointer]);
    typeStack[stackPointer] = TYPE_I64;
    return nextCommand;
  }
}
