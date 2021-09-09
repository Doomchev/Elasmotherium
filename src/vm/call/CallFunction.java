package vm.call;

import ast.function.CustomFunction;
import vm.VMCommand;

public class CallFunction extends VMCommand {
  private final CustomFunction function;

  public CallFunction(CustomFunction function) {
    this.function = function; 
  }
  
  public static void execute(CustomFunction function) {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    currentCall = new VMFunctionCall(
        stackPointer - function.getCallAllocation()
        , function.getCallDeallocation());
    currentCommand = function.getStartingCommand();
  }
  
  @Override
  public void execute() {
    execute(function);
  }

  @Override
  public String toString() {
    return "Call " + function + "(" + function.getStartingCommand() + "):";
  }
}
