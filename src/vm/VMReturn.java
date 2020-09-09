package vm;

public class VMReturn extends Command {
  @Override
  public Command execute() {
    VMFunctionCall call = currentCall;
    callStackPointer--;
    currentCall = callStack[callStackPointer];
    return call.returnPoint;
  }  
}
