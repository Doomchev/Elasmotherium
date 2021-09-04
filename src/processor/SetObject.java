package processor;

import base.ElException;

public class SetObject extends ProCommand {
  static final SetObject instance = new SetObject(null);
  
  private final ProParameter parameter;

  private SetObject(ProParameter value) {
    this.parameter = value;
  }

  @Override
  ProCommand create(String param) throws ElException {
    return new SetObject(ProParameter.get(param));
  }

  @Override
  void execute() throws ElException {
    object = parameter.getValue();
    if(log) log("Set object to " + parameter);
  }
}
