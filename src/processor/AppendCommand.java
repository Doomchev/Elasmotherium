package processor;

import base.ElException;
import vm.VMCommand;

public class AppendCommand extends ProCommand {
  private final VMCommand command;
  private final ProParameter parameter;
  
  public AppendCommand(VMCommand command, String parameter)
      throws ElException {
    super();
    this.command = command;
    this.parameter = ProParameter.get(parameter);
  }

  @Override
  void execute() throws ElException {
    append(command.create(parameter));
  } 
}
