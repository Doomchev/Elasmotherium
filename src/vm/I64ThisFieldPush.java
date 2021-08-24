package vm;

import base.ElException;
import static vm.VMBase.i64Stack;

public class I64ThisFieldPush extends VMFieldCommand {
  public I64ThisFieldPush(int fieldIndex) {
    super(fieldIndex);
  }
  
  @Override
  public VMCommand create(int index) throws ElException {
    return new I64ThisFieldPush(index);
  }
  
  @Override
  public void execute() throws ElException {
    stackPointer++;
    i64Stack[stackPointer] = objectStack[currentCall.paramPosition - 1]
        .fields[fieldIndex].i64Get();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
