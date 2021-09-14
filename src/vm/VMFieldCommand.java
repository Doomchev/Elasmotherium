package vm;

import base.ElException;
import processor.parameter.ProParameter;

public abstract class VMFieldCommand extends VMCommand {
  protected final int fieldIndex;

  public VMFieldCommand(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return create(parameter.getIndex());
  }
  
  public abstract VMCommand create(int index) throws ElException;
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
