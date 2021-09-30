package vm;

import base.ElException;
import base.ElException.CannotCreate;
import base.EntityException;
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
  
  public VMCommand create(ProParameter parameter)
      throws EntityException, ElException {
    return create();
  }
  
  public VMCommand create(LinkedList<ProParameter> parameters)
      throws ElException, EntityException {
    throw new CannotCreate(this, toString() + " with "
        + parameters.size() + " parameters");
  }
  
  public abstract void execute() throws ElException;
  
  public void setPosition(int command) {
  }
}
