package vm;

public class CallParam extends VMCommand {
  @Override
  public void execute() {
    currentCall.paramPosition = stackPointer + 1;
    currentCommand++;
  }
}
