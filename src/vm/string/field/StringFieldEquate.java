package vm.string.field;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringFieldEquate extends VMFieldCommand {
  public StringFieldEquate(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new StringFieldEquate(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public void execute() throws ElException {
    valueStack[currentCall.varIndex(varIndex)].getField(fieldIndex)
        .stringSet(stringStack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
