package vm.object;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class ObjectFieldEquate extends VMFieldCommand {
  public ObjectFieldEquate(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }

  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new ObjectFieldEquate(fieldIndex, varIndex, proLine, entity);
  }

  @Override
  public void execute() throws ElException {
    valueStack[currentCall.varIndex(varIndex)].setField(
        fieldIndex, valueStack[stackPointer]);
    stackPointer -= varIndex == LAST ? 2 : 1;
    currentCommand++;
  }
}
