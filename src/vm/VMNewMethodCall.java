package vm;

public class VMNewMethodCall extends Command {
  @Override
  public void execute() {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    currentCall = new VMFunctionCall(objectStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
}
