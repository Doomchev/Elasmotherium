package vm;

public class IfFalseGoto extends VMCommand {
  public VMCommand command;

  public IfFalseGoto() {
  }
  
  public IfFalseGoto(VMCommand command) {
    this.command = command;
  }
  
  @Override
  public void setGoto(VMCommand command) {
    this.command = command;
  }
  
  @Override
  public void execute() {
    stackPointer--;
    currentCommand = booleanStack[stackPointer + 1] ? nextCommand : command;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command.lineNum;
  }
}
