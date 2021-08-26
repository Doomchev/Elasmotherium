package vm;

import ast.ClassEntity;
import base.ElException;

public class ObjectNew extends VMCommand {
  private final ClassEntity classEntity;

  public ObjectNew(ClassEntity classEntity) {
    this.classEntity = classEntity;
  }

  @Override
  public void execute() throws ElException {
    stackPointer++;
    objectStack[stackPointer] = classEntity.newObject();
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + classEntity.toString();
  }
}
