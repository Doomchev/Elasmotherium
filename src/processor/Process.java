package processor;

import processor.parameter.ProParameter;
import base.ElException;

public class Process extends ProCommand {
  public static final Process instance = new Process(null);
  
  private final ProParameter parameter;

  private Process(ProParameter parameter) {
    this.parameter = parameter;
  }
  
  @Override
  public Process create(String param) throws ElException {
    return new Process(ProParameter.get(param));
  }
  
  @Override
  public void execute() throws ElException {
    parameter.getValue().process();
  }
}
