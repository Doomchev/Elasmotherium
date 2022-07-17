package vm.call;

import ast.Entity;
import ast.function.CustomFunction;
import vm.VMCommand;

public class CallFunction extends VMCommand {
  private final CustomFunction function;

  public CallFunction(CustomFunction function, int proLine, Entity entity) {
    super(proLine, entity);
    this.function = function; 
  }
  
  public static void execute(CustomFunction function) {
    callStackPointer++;
    callStack[callStackPointer] = currentCall;
    int isMethod = function.isMethod() ? 1 : 0;
    int isConstructor = function.isConstructor() ? 1 : 0;
    int variablesQuantity = function.getVariablesQuantity();
    int parametersQuantity = function.getParametersQuantity();
    int deallocation = variablesQuantity + parametersQuantity + isMethod;
    int paramPosition = stackPointer + 1 - parametersQuantity;
    currentCall = new VMFunctionCall(paramPosition, deallocation);
    System.out.println("(parampos = " + paramPosition + ", varpos = "
        + (stackPointer + 1) + ", dealloc = " + deallocation + ")");
    stackPointer += variablesQuantity;
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
