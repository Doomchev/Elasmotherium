package vm;

import base.ElException;
import processor.ProBase;
import processor.ProParameter;

public abstract class VMFieldCommand extends VMCommand {
  int fieldIndex;

  public VMFieldCommand(int fieldIndex) {
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public VMCommand create(ProParameter parameter) throws ElException {
    return create(ProBase.getIndex());
  }
  
  public abstract VMCommand create(int index) throws ElException;
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
