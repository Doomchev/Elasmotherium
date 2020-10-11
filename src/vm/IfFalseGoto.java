package vm;

public class IfFalseGoto extends Command {
  public Command command;

  public IfFalseGoto() {
  }
  
  public IfFalseGoto(Command command) {
    this.command = command;
  }
  
  @Override
  public void setGoto(Command command) {
    this.command = command;
  }
  
  @Override
  public void execute() {
    stackPointer--;
    currentCommand = booleanStack[stackPointer + 1] ? nextCommand : command;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command.number;
  }
}
