package vm.object;

import ast.exception.ElException;
import ast.exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class ObjectVarEquate extends VMCommand {
  private final int index;

  public ObjectVarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter)
      throws ElException, EntityException {
    return new ObjectVarEquate(parameter.getValue().getIndex());
  }
  
  @Override
  public void execute() {
    int stackIndex = currentCall.varIndex(index);
    objectStack[stackIndex] = objectStack[stackPointer];
    if(log) typeStack[stackIndex] = ValueType.OBJECT;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
