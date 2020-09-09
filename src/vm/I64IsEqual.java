package vm;

public class I64IsEqual extends Command {
  @Override
  public Command execute() {
    i64StackPointer -= 2;
    booleanStackPointer++;
    booleanStack[booleanStackPointer] = i64Stack[i64StackPointer + 1]
        == i64Stack[i64StackPointer + 2];
    return nextCommand;
  }
}
