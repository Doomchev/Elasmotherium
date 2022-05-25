package processor;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;

public class GetSubType extends ProCommand {
  static final GetSubType instance = new GetSubType(null);

  private final ProParameter parameter;

  private GetSubType(ProParameter parameter) {
    super();
    this.parameter = parameter;
  }
  
  @Override
  public ProCommand create(String param) throws ElException {
    return new GetSubType(ProParameter.get(param));
  }

  @Override
  public void execute() throws ElException, EntityException {
    if(log) println("Getting subtype of " + currentObject + ".");
    currentObject = currentObject.getSubType();
  }  
}
