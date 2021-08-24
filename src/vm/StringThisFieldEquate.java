package vm;

import base.ElException;
import static vm.VMBase.objectStack;

public class StringThisFieldEquate extends VMFieldCommand {
  public StringThisFieldEquate(int fieldIndex) {
    super(fieldIndex);
  }

  @Override
  public VMCommand create(int index) throws ElException {
    return new StringThisFieldEquate(index);
  }
  
  @Override
  public void execute() throws ElException {
    objectStack[currentCall.paramPosition - 1]
        .fields[fieldIndex].stringSet(stringStack[stackPointer]);
    stackPointer--;
    currentCommand++;
  }
}
