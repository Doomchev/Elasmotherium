package processor;

import processor.parameter.ProParameter;
import exception.ElException;
import exception.EntityException;

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
  public void execute() throws ElException, EntityException {
    parameter.getValue().process();
  }
}
