package vm;

import ast.Function;

public class CallFunction extends VMCommand {
  private final Function function;

  public CallFunction(Function function) {
    this.function = function; 
  }
  
  public static void execute(Function function) {
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
