package processor;

import base.ElException;

public class Process extends ProCommand {
  public static final Process instance = new Process(null);
  
  private final ProParameter parameter;

  private Process(ProParameter parameter) {
    this.parameter = parameter;
  }
  
  @Override
  Process create(String param) throws ElException {
    return new Process(ProParameter.get(param));
  }
  
  @Override
  void execute() throws ElException {
    parameter.getValue().process();
  }
}
