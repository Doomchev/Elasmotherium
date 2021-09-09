package vm;

import base.ElException;
import java.util.LinkedList;
import processor.ProParameter;

public abstract class VMCommand extends VMBase {
  private int lineNum;
  
  public VMCommand create() throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new ElException("Cannot create ", this);
    }
  }
  
  public VMCommand create(ProParameter parameter) throws ElException {
    return create();
  }
  
  public VMCommand create(LinkedList<ProParameter> parameters)
      throws ElException {
    throw new ElException("Cannot create " + toString() + " with "
        + parameters.size() + " parameters.");
  }
  
  public abstract void execute() throws ElException;
  
  public void setPosition(int command) {
  }
  
  public void log() {
    System.out.println(subIndent.toString() + lineNum + ": " + toString());
  }
}
