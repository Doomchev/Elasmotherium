package vm;

public class StringAdd extends Command {
  @Override
  public Command execute() {
    stringStackPointer--;
    stringStack[stringStackPointer] += stringStack[stringStackPointer + 1];
    return nextCommand;
  }
}
