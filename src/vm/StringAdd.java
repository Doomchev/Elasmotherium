package vm;

public class StringAdd extends Command {
  @Override
  public void execute() {
    stackPointer--;
    stringStack[stackPointer] += stringStack[stackPointer + 1];
    currentCommand = nextCommand;
  }
}
