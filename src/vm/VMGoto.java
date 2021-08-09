package vm;

public class VMGoto extends VMCommand {
  public VMCommand command;

  public VMGoto() {
  }
  
  public VMGoto(VMCommand command) {
    this.command = command;
  }
  
  @Override
  public void setGoto(VMCommand command) {
    this.command = command;
  }
  
  @Override
  public void execute() {
    currentCommand = command;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command.lineNum;
  }
}
