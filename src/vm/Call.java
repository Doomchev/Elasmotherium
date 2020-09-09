package vm;

import parser.structure.ID;

public class Call extends Command {
  Command functionCommand;
  ID name;

  public Call(Command functionCommand) {
    this.functionCommand = functionCommand;
  }
  
  @Override
  public Command execute() {
    currentCall.returnPoint = nextCommand;
    return functionCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + name.string;
  }
}
