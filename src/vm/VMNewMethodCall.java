package vm;

public class VMNewMethodCall extends VMCommand {
  @Override
  public void execute() {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    currentCall = new VMFunctionCall(objectStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
}
