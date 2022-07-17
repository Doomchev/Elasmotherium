package vm.object;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectVarPush extends VMCommand {
  private final int index;

  public ObjectVarPush(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException, EntityException {
    return new ObjectVarPush(parameter.getIndex(), proLine, entity);
  }
  
  @Override
  public void execute() {
    stackPointer++;
    valueStack[stackPointer] = valueStack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
