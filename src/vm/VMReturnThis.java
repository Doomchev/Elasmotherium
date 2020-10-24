package vm;

public class VMReturnThis extends Command {
  @Override
  public void execute() {
    VMFunctionCall call = currentCall;
    
    stackPointer++;
    objectStack[stackPointer] = call.thisObject;
    typeStack[stackPointer] = TYPE_OBJECT;
    
    currentCall = callStack[callStackPointer];
    callStackPointer--;
    currentCommand = call.returnPoint;
  }  
}
