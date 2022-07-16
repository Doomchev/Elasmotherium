package processor;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

import java.util.LinkedList;

public class AppendCommand extends ProCommand {
  public static class AppendCommand1 extends AppendCommand {
    private final ProParameter parameter;
  
    public AppendCommand1(VMCommand command, String parameter, int proLine)
        throws ElException {
      super(command, proLine);
      this.parameter = ProParameter.get(parameter);
    }

    @Override
    public void execute() throws ElException, EntityException {
      append(command.create(parameter), line);
    } 
  }
  
  public static class AppendCommand2 extends AppendCommand {
    private final LinkedList<ProParameter> parameters = new LinkedList<>();

    public AppendCommand2(VMCommand command, String[] parts, int proLine)
        throws ElException {
      super(command, proLine);
      for(String part: parts) parameters.add(ProParameter.get(part));
    }

    @Override
    public void execute() throws ElException, EntityException {
      append(command.create(parameters), line);
    } 
  }
  
  protected final VMCommand command;
  
  public AppendCommand(VMCommand command, int proLine) {
    this.command = command;
    command.proLine = proLine;
    this.line = currentLineReader.getLineNum();
  }
  
  public static ProCommand create(VMCommand command, String parameters
      , int proLine) throws ElException {
    if(parameters.isEmpty()) return new AppendCommand(command, proLine);
    String[] parts = trimmedSplit(parameters, ',');
    if(parts.length == 1) return new AppendCommand1(command, parts[0], proLine);
    return new AppendCommand2(command, parts, proLine);
  }

  @Override
  public void execute() throws ElException, EntityException {
    append(command.create(), line);
  } 
}
