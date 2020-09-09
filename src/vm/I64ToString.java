package vm;

public class I64ToString extends Command {
  @Override
  public Command execute() {
    stringStackPointer++;
    stringStack[stringStackPointer] = String.valueOf(i64Stack[i64StackPointer]);
    i64StackPointer--;
    return nextCommand;
  }
}
