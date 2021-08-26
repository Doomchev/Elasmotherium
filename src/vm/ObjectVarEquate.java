package vm;

import base.ElException;
import processor.ProParameter;

public class ObjectVarEquate extends VMCommand {
  private final int index;

  public ObjectVarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public ObjectVarEquate create(ProParameter parameter) throws ElException {
    return new ObjectVarEquate(parameter.getValue().getIndex());
  }
  
  @Override
  public void execute() {
    int stackIndex = index + currentCall.paramPosition;
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
