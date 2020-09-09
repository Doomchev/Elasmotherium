package vm;

public class I64Multiply extends Command {
  @Override
  public Command execute() {
    i64StackPointer--;
    i64Stack[i64StackPointer] *= i64Stack[i64StackPointer + 1];
    return nextCommand;
  }
}
