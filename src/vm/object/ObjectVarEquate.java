package vm.object;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectVarEquate extends VMCommand {
  private final int index;

  public ObjectVarEquate(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new ObjectVarEquate(parameter.getValue().getIndex(), proLine, entity);
  }
  
  @Override
  public void execute() {
    int stackIndex = currentCall.varIndex(index);
    valueStack[stackIndex] = valueStack[stackPointer];
    if(log) typeStack[stackIndex] = ValueType.OBJECT;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
