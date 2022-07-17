package processor.parameter;

import exception.ElException;
import exception.EntityException;
import processor.ProCommand;

public class SetParameter extends ProCommand {
  public static final SetParameter instance = new SetParameter(null, 0);
  
  private final ProParameter parameter;

  private SetParameter(ProParameter value, int proLine) {
    super(proLine);
    this.parameter = value;
    this.line = currentLineReader.getLineNum();
  }

  @Override
  public ProCommand create(String param, int proLine) throws ElException {
    return new SetParameter(ProParameter.get(param), proLine);
  }

  @Override
  public void execute() throws ElException, EntityException {
    currentParam = parameter.getValue().getType();
    if(log) log("Set current type to " + currentParam);
  }
}
