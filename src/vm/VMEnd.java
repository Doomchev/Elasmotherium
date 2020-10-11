package vm;

public class VMEnd extends Command {
  @Override
  public void execute() {
    currentCommand = null;
  }
}
