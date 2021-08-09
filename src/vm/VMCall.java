package vm;

import ast.Function;

public class VMCall extends VMCommand {
  Function function;

  public VMCall(Function function) {
    this.function = function;
  }
  
  @Override
  public void execute() {
    currentCall.returnPoint = nextCommand;
    currentCommand = function.startingCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + function.toString() + " ("
        + function.startingCommand.lineNum + ")";
  }
}
