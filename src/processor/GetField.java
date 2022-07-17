package processor;

import ast.Entity;
import processor.parameter.ProParameter;
import exception.ElException;
import exception.EntityException;

public class GetField extends ProCommand {
  static final GetField instance = new GetField(null, 0);

  private final ProParameter parameter;

  private GetField(ProParameter parameter, int proLine) {
    super(proLine);
    this.parameter = parameter;
  }
  
  @Override
  public ProCommand create(String param, int proLine) throws ElException {
    return new GetField(ProParameter.get(param), 0);
  }

  @Override
  public void execute() throws ElException, EntityException {
    if(log) println("Getting field of " + currentObject + ".");
    Entity old = currentParam;
    currentObject = currentObject.getField();
    currentParam = old;
  }  
}
