package processor;

import processor.parameter.ProParameter;
import exception.ElException;
import exception.EntityException;

public class Compile extends ProCommand {
  public static final Compile instance = new Compile(null);
  
  private final ProParameter parameter;

  private Compile(ProParameter parameter) {
    this.parameter = parameter;
  }
  
  @Override
  public Compile create(String param) throws ElException {
    return new Compile(ProParameter.get(param));
  }
  
  @Override
  public void execute() throws ElException, EntityException {
    parameter.getValue().compile();
  }
}
