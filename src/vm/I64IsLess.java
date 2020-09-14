package vm;

public class I64IsLess extends Command {
  @Override
  public Command execute() {
    stackPointer--;
    booleanStack[stackPointer]
        = i64Stack[stackPointer] < i64Stack[stackPointer + 1];
    typeStack[stackPointer] = TYPE_BOOLEAN;
    return nextCommand;
  }
}
