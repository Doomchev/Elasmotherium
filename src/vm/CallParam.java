package vm;

public class CallParam extends Command {
  @Override
  public Command execute() {
    currentCall.paramPosition = stackPointer + 1;
    return nextCommand;
  }
}
