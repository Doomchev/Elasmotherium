package processor;

import base.ElException;
import vm.VMCommand;

public class AppendCommand extends ProCommand {
  VMCommand command;
  ProParameter parameter;
  
  public AppendCommand(VMCommand command, String parameter)
      throws ElException {
    super();
    this.command = command;
    this.parameter = ProParameter.get(parameter);
  }

  @Override
  void execute() throws ElException {
    VMCommand newCommand = command.create(parameter);
    append(newCommand);
  } 
}
