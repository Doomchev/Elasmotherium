package vm;

public class VMReturn extends Command {
  @Override
  public Command execute() {
    VMFunctionCall call = currentCall;
    callStackPointer--;
    if(callStackPointer >= 0) {
      currentCall = callStack[callStackPointer];
    } else {
      currentCall = null;
    }
    return call.returnPoint;
  }  
}
