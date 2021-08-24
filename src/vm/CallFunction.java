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
    int params = function.parameters.size();
    int isMethod = function.isMethod() ? 1 : 0;
    int isConstructor = function.isConstructor ? 1 : 0;
    currentCall = new VMFunctionCall(null
        , stackPointer - params + 1
        , params + isMethod - isConstructor);
    currentCommand = function.startingCommand;
  }
  
  @Override
  public void execute() {
    execute(function);
  }

  @Override
  public String toString() {
    return "Call " + function + "(" + function.startingCommand + "):";
  }
}
