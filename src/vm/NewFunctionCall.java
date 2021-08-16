package vm;

public class NewFunctionCall extends VMCommand {
  @Override
  public void execute() {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    currentCall = new VMFunctionCall(null);
    currentCommand++;
  }
}
