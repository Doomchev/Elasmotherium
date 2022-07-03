package processor;

import ast.Entity;
import processor.parameter.ProParameter;
import exception.ElException;
import exception.EntityException;

public class GetField extends ProCommand {
  static final GetField instance = new GetField(null);

  private final ProParameter parameter;

  private GetField(ProParameter parameter) {
    super();
    this.parameter = parameter;
  }
  
  @Override
  public ProCommand create(String param) throws ElException {
    return new GetField(ProParameter.get(param));
  }

  @Override
  public void execute() throws ElException, EntityException {
    if(log) println("Getting field of " + currentObject + ".");
    Entity old = currentParam;
    currentObject = currentObject.getField();
    currentParam = old;
  }  
}
