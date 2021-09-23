package vm;

import base.ElException;
import base.ElException.CannotCreate;
import java.util.LinkedList;
import processor.parameter.ProParameter;

public abstract class VMCommand extends VMBase {
  public VMCommand create() throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new CannotCreate(this);
    }
  }
  
  public VMCommand create(ProParameter parameter) throws ElException {
    return create();
  }
  
  public VMCommand create(LinkedList<ProParameter> parameters)
      throws ElException {
    throw new CannotCreate(this, toString() + " with "
        + parameters.size() + " parameters");
  }
  
  public abstract void execute() throws ElException;
  
  public void setPosition(int command) {
  }
}
