package vm;

import base.ElException;
import processor.ProBase;
import processor.parameter.ProParameter;

public abstract class VMFieldCommand extends VMCommand {
  public static final int OBJECT = -1, LAST = -2;
  
  protected final int fieldIndex, varIndex;

  public VMFieldCommand(int fieldIndex, int varIndex) {
    this.fieldIndex = fieldIndex;
    this.varIndex = varIndex;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return create(ProBase.currentObject.getIndex(), parameter.getIndex());
  }
      
  public abstract VMCommand create(int fieldIndex, int varIndex)
      throws ElException;
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
