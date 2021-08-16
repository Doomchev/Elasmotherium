package processor;

import base.ElException;
import vm.VMBase;
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
    if(log) log(newCommand.toString());
    VMBase.append(newCommand);
  } 
}
