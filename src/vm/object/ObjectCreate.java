package vm.object;

import ast.ClassEntity;
import vm.VMCommand;

public class ObjectCreate extends VMCommand {
  private final ClassEntity classEntity;

  public ObjectCreate(ClassEntity classEntity) {
    this.classEntity = classEntity;
  }

  @Override
  public void execute() {
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
