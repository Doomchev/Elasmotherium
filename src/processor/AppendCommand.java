package processor;

import processor.parameter.ProParameter;
import base.ElException;
import base.EntityException;
import java.util.LinkedList;
import vm.VMCommand;

public class AppendCommand extends ProCommand {
  public static class AppendCommand1 extends AppendCommand {
    private final ProParameter parameter;
  
    public AppendCommand1(VMCommand command, String parameter)
        throws ElException {
      super(command);
      this.parameter = ProParameter.get(parameter);
    }

    @Override
    public void execute() throws ElException, EntityException {
      append(command.create(parameter));
    } 
  }
  
  public static class AppendCommand2 extends AppendCommand {
    private final LinkedList<ProParameter> parameters = new LinkedList<>();

    public AppendCommand2(VMCommand command, String[] parts)
        throws ElException {
      super(command);
      for(String part: parts) parameters.add(ProParameter.get(part));
    }

    @Override
    public void execute() throws ElException, EntityException {
      append(command.create(parameters));
    } 
  }
  
  protected final VMCommand command;
  
  public AppendCommand(VMCommand command)
      throws ElException {
    this.command = command;
  }
  
  public static ProCommand create(VMCommand command, String parameters)
      throws ElException {
    if(parameters.isEmpty()) return new AppendCommand(command);
    String[] parts = trimmedSplit(parameters, ',');
    if(parts.length == 1) return new AppendCommand1(command, parts[0]);
    return new AppendCommand2(command, parts);
  }

  @Override
  public void execute() throws ElException, EntityException {
    append(command.create());
  } 
}
