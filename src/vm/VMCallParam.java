package vm;

public class VMCallParam extends Command {
  @Override
  public void execute() {
    currentCall.paramPosition = stackPointer + 1;
    currentCommand = nextCommand;
  }
}
