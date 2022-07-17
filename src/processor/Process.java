package processor;

import processor.parameter.ProParameter;
import exception.ElException;
import exception.EntityException;

public class Process extends ProCommand {
  public static final Process instance = new Process(null, 0);
  
  private final ProParameter parameter;

  private Process(ProParameter parameter, int proLine) {
    super(proLine);
    this.parameter = parameter;
  }
  
  @Override
  public Process create(String param, int proLine) throws ElException {
    return new Process(ProParameter.get(param), 0);
  }
  
  @Override
  public void execute() throws ElException, EntityException {
    parameter.getValue().compile();
  }
}
