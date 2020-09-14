package vm;

public class I64Subtract extends Command {
  @Override
  public Command execute() {
    stackPointer--;
    i64Stack[stackPointer] -= i64Stack[stackPointer + 1];
    return nextCommand;
  }
}
