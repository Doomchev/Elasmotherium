package vm.functions;

import vm.VMCommand;

public class Exit extends VMCommand {
  @Override
  public void execute() {
    System.exit(0);
  }
}
