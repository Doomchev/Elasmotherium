package vm.string.field;

import ast.Entity;
import exception.ElException;
import vm.VMCommand;
import vm.VMFieldCommand;

public class StringFieldPush extends VMFieldCommand {
  public StringFieldPush(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public VMCommand create(int fieldIndex, int varIndex, int proLine, Entity entity) {
    return new StringFieldPush(fieldIndex, varIndex, proLine, entity);
  }
  
  @Override
  public void execute() throws ElException {
    if(varIndex != LAST) stackPointer++;
    stringStack[stackPointer] = valueStack[currentCall.varIndex(varIndex)]
        .getField(fieldIndex).stringGet();
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
}
