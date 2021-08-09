package vm;

public class VMCallParam extends VMCommand {
  @Override
  public void execute() {
    currentCall.paramPosition = stackPointer + 1;
    currentCommand = nextCommand;
  }
}
