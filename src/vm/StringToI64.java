package vm;

public class StringToI64 extends Command {
  @Override
  public Command execute() {
    i64StackPointer++;
    i64Stack[i64StackPointer] = Long.parseLong(stringStack[stringStackPointer]);
    stringStackPointer--;
    return nextCommand;
  }
}
