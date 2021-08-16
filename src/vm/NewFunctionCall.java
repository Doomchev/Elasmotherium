package vm;

import ast.Function;

public class NewFunctionCall extends VMCommand {
  Function function;

  public NewFunctionCall(Function function) {
    this.function = function;
  }
  
  @Override
  public void execute() {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    currentCall = new VMFunctionCall(null, stackPointer + 1
        - function.parameters.size());
    currentCommand = function.startingCommand;
  }

  @Override
  public String toString() {
    return "call " + function + "(" + function.startingCommand + ")";
  }
}
