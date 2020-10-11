package vm;

public class VMGoto extends Command {
  public Command command;

  public VMGoto() {
  }
  
  public VMGoto(Command command) {
    this.command = command;
  }
  
  @Override
  public void setGoto(Command command) {
    this.command = command;
  }
  
  @Override
  public void execute() {
    currentCommand = command;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command.number;
  }
}
