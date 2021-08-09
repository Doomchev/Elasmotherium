package vm;

public class VMReturn extends VMCommand {
  @Override
  public void execute() {
    currentCommand = currentCall.returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }  
}
