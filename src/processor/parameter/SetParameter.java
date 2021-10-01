package processor.parameter;

import ast.exception.ElException;
import ast.exception.EntityException;
import processor.ProCommand;

public class SetParameter extends ProCommand {
  public static final SetParameter instance = new SetParameter(null);
  
  private final ProParameter parameter;

  private SetParameter(ProParameter value) {
    this.parameter = value;
  }

  @Override
  public ProCommand create(String param) throws ElException {
    return new SetParameter(ProParameter.get(param));
  }

  @Override
  public void execute() throws ElException, EntityException {
    currentParam = parameter.getValue();
    if(log) log("Set current type to " + currentParam);
  }
}
