package vm;

public class Return extends VMCommand {
  @Override
  public void execute() {
    currentCommand = currentCall.returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }  
}
