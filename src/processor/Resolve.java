package processor;

import base.ElException;

public class Resolve extends ProCommand {
  public static final Resolve instance = new Resolve(null);
  
  private final ProParameter parameter;

  private Resolve(ProParameter parameter) {
    this.parameter = parameter;
  }
  
  @Override
  Resolve create(String param) throws ElException {
    return new Resolve(ProParameter.get(param));
  }
  
  @Override
  void execute() throws ElException {
    parameter.getValue().process();
  }
}
