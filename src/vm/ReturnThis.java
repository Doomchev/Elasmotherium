package vm;

public class ReturnThis extends VMCommand {
  @Override
  public void execute() {
    VMFunctionCall call = currentCall;
    
    stackPointer++;
    objectStack[stackPointer] = call.thisObject;
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    
    currentCall = callStack[callStackPointer];
    callStackPointer--;
    currentCommand = call.returnPoint;
  }  
}
