package vm.string;

import base.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringFieldPush extends VMFieldCommand {
  public StringFieldPush(int fieldIndex) {
    super(fieldIndex);
  }
  
  @Override
  public VMCommand create(int index) throws ElException {
    return new StringFieldPush(index);
  }
  
  @Override
  public void execute() throws ElException {
    stringStack[stackPointer]
        = objectStack[stackPointer].getField(fieldIndex).stringGet();
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
}
