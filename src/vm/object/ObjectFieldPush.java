package vm.object;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class ObjectFieldPush extends VMFieldCommand {
  public ObjectFieldPush(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new ObjectFieldPush(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public void execute() throws ElException {
    if(varIndex != LAST) stackPointer++;
    valueStack[stackPointer] = valueStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).objectGet();
    if(log) typeStack[stackPointer] = ValueType.I64;
    currentCommand++;
  }
}
