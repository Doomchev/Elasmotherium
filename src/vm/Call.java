package vm;

import parser.structure.Function;

public class Call extends Command {
  Function function;

  public Call(Function function) {
    this.function = function;
  }
  
  @Override
  public Command execute() {
    currentCall.returnPoint = nextCommand;
    return function.startingCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + function.toString() + " ("
        + function.startingCommand.number + ")";
  }
}
