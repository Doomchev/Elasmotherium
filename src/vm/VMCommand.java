package vm;

import ast.ClassEntity;
import ast.Entity;
import ast.ObjectEntity;
import ast.Variable;
import base.ElException;

public abstract class VMCommand extends VMBase {
  public VMCommand nextCommand;
  public int lineNum;
  
  public VMCommand create(Entity entity) throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new ElException("Cannot create ", this);
    }
  }
  
  public abstract void execute() throws ElException;
  
  public void setGoto(VMCommand command) {
  }

  public ObjectEntity newObject(ClassEntity classEntity) {
    ObjectEntity object = new ObjectEntity(classEntity);
    int index = -1;
    object.fields = new VMValue[classEntity.fields.size()];
    for(Variable parameter : classEntity.fields) {
      index++;
      object.fields[index] = parameter.type.createValue();
    }
    return object;
  }
  
  public void log() {
    System.out.println(subIndent + lineNum + ": " + toString());
  }
  
  @Override
  public String toString() {
    return getName();
  }
}
