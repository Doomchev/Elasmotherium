package vm;

public class I64Multiply extends VMCommand {
  @Override
  public void execute() {
    stackPointer--;
    i64Stack[stackPointer] *= i64Stack[stackPointer + 1];
    currentCommand = nextCommand;
  }
}
