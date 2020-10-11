package vm;

public class VMReturn extends Command {
  @Override
  public void execute() {
    currentCommand = currentCall.returnPoint;
    currentCall = callStack[callStackPointer];
    callStackPointer--;
  }  
}
