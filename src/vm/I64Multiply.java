package vm;

public class I64Multiply extends Command {
  @Override
  public Command execute() {
    stackPointer--;
    i64Stack[stackPointer] *= i64Stack[stackPointer + 1];
    return nextCommand;
  }
}
