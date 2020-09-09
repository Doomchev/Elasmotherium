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
  public Command execute() {
    return command;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command.number;
  }
}
