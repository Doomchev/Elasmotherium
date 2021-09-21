package processor.parameter;

import base.ElException;
import processor.ProCommand;

public class SetType extends ProCommand {
  public static final SetType instance = new SetType(null);
  
  private final ProParameter parameter;

  private SetType(ProParameter value) {
    this.parameter = value;
  }

  @Override
  public ProCommand create(String param) throws ElException {
    return new SetType(ProParameter.get(param));
  }

  @Override
  public void execute() throws ElException {
    currentParam = parameter.getValue();
    if(log) log("Set current type to " + currentParam);
  }
}
