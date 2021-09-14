package processor.parameter;

import base.ElException;
import processor.ProCommand;

public class SetObject extends ProCommand {
  public static final SetObject instance = new SetObject(null);
  
  private final ProParameter parameter;

  private SetObject(ProParameter value) {
    this.parameter = value;
  }

  @Override
  public ProCommand create(String param) throws ElException {
    return new SetObject(ProParameter.get(param));
  }

  @Override
  public void execute() throws ElException {
    object = parameter.getValue();
    if(log) log("Set object to " + parameter);
  }
}
