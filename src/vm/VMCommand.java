package vm;

import base.ElException;
import processor.ProParameter;

public abstract class VMCommand extends VMBase {
  private int lineNum;
  
  public VMCommand create(ProParameter parameter) throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new ElException("Cannot create ", this);
    }
  }
  
  public abstract void execute() throws ElException;
  
  public void setGoto(int command) {
  }
  
  public void log() {
    System.out.println(subIndent + lineNum + ": " + toString());
  }
  
  @Override
  public String toString() {
    return getClassName();
  }
}
