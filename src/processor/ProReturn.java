package processor;

import base.ElException;

public class ProReturn extends ProCommand {
  ProParameter parameter;

  public ProReturn(ProParameter parameter) {
    this.parameter = parameter;
  }

  @Override
  ProCommand create(String param) throws ElException {
    return new ProReturn(ProParameter.get(param));
  }

  @Override
  void execute() throws ElException {
    setReturnType(parameter.getValue());
  }
}
