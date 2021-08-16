package vm;

public class Exit extends VMCommand {
  @Override
  public void execute() {
    System.exit(0);
  }
}
