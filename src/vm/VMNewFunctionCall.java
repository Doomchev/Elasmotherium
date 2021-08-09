package vm;

public class VMNewFunctionCall extends VMCommand {
  @Override
  public void execute() {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    currentCall = new VMFunctionCall(null);
    currentCommand = nextCommand;
  }
}
