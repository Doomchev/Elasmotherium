package vm.object;

import ast.ClassEntity;
import ast.Entity;
import vm.VMCommand;

public class ObjectCreate extends VMCommand {
  private final ClassEntity classEntity;

  public ObjectCreate(ClassEntity classEntity, int proLine, Entity entity) {
    super(proLine, entity);
    this.classEntity = classEntity;
  }

  @Override
  public void execute() {
    stackPointer++;
    valueStack[stackPointer] = classEntity.newObject();
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + classEntity.toString();
  }
}
