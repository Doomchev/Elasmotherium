package vm;

public class I64Less extends Command {
  @Override
  public Command execute() {
    booleanStackPointer++;
    booleanStack[booleanStackPointer] =
        i64Stack[i64StackPointer - 1] < i64Stack[i64StackPointer];
    i64StackPointer -= 2;
    return nextCommand;
  }
}
