package vm;

import ast.ClassEntity;
import ast.Entity;
import ast.ObjectEntity;
import ast.Variable;
import base.ElException;

public abstract class Command extends VMBase {
  public Command nextCommand;
  public int number;
  
  public abstract void execute() throws ElException;
  
  public void setGoto(Command command) {
  }

  public ObjectEntity newObject(ClassEntity classEntity) {
    ObjectEntity object = new ObjectEntity(classEntity);
    int index = -1;
    object.fields = new Entity[classEntity.fields.size()];
    for(Variable parameter : classEntity.fields) {
      index++;
      object.fields[index] = parameter.type.createValue();
    }
    return object;
  }
  
  @Override
  public String toString() {
    return number + ": " + getClass().getSimpleName();
  }
}
