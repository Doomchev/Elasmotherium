package vm.i64.field;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class I64ThisFieldIncrement extends VMFieldCommand {
  public I64ThisFieldIncrement(int fieldIndex) {
    super(fieldIndex);
  }

  @Override
  public VMCommand create(int index) throws ElException {
    return new I64ThisFieldIncrement(index);
  }
  
  @Override
  public void execute() throws ElException {
    currentCall.thisField(fieldIndex).increment();
    currentCommand++;
  }
}