package processor;

import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;

public class GetSubType extends ProCommand {
  static final GetSubType instance = new GetSubType(null, 0);

  private final ProParameter parameter;

  private GetSubType(ProParameter parameter, int proLine) {
    super(proLine);
    this.parameter = parameter;
  }
  
  @Override
  public ProCommand create(String param, int proLine) throws ElException {
    return new GetSubType(ProParameter.get(param), proLine);
  }

  @Override
  public void execute() throws ElException, EntityException {
    if(log) println("Getting subtype of " + currentObject + ".");
    currentObject = currentObject.getSubtype(0);
  }  
}
