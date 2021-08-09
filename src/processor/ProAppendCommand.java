package processor;

import base.ElException;
import vm.VMBase;
import vm.VMCommand;

public class ProAppendCommand extends ProCommand {
  VMCommand command;
  
  public ProAppendCommand(VMCommand command) {
    super();
    this.command = command;
  }

  @Override
  void execute() throws ElException {
    VMCommand newCommand = command.create(current);
    if(log) log(newCommand.toString());
    VMBase.append(newCommand);
  } 
}
