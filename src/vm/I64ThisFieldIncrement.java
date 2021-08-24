package vm;

import base.ElException;
import static vm.VMBase.objectStack;

public class I64ThisFieldIncrement extends VMFieldCommand {
  public I64ThisFieldIncrement(int fieldIndex) {
    super(fieldIndex);
  }

  @Override
  public VMCommand create(int index) throws ElException {
    return new I64FieldIncrement(index);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[currentCall.paramPosition - 1].fields[fieldIndex].increment();
    stackPointer--;
    currentCommand++;
  }
}
