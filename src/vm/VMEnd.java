package vm;

public class VMEnd extends VMCommand {
  @Override
  public void execute() {
    currentCommand = null;
  }
}
