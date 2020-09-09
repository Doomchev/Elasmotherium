package vm;

public class NewFunctionCall extends Command {

  @Override
  public Command execute() {
    currentCall = new VMFunctionCall();
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    return nextCommand;
  }
}
