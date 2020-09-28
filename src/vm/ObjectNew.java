package vm;

import ast.ClassEntity;
import ast.Entity;
import ast.ObjectEntity;
import ast.Variable;

public class ObjectNew extends Command {
  public ClassEntity classEntity;

  public ObjectNew(ClassEntity classEntity) {
    this.classEntity = classEntity;
  }

  @Override
  public Command execute() {
    ObjectEntity object = new ObjectEntity(classEntity);
    int index = -1;
    object.fields = new Entity[classEntity.fields.size()];
    for(Variable parameter : classEntity.fields) {
      index++;
      object.fields[index] = parameter.type.createValue();
    }
    stackPointer++;
    objStack[stackPointer] = object;
    typeStack[stackPointer] = TYPE_OBJECT;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + classEntity.toString();
  }
}
