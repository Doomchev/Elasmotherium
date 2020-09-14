package vm;

public class StringAdd extends Command {
  @Override
  public Command execute() {
    stackPointer--;
    stringStack[stackPointer] += stringStack[stackPointer + 1];
    return nextCommand;
  }
}
