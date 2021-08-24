package vm;

import ast.ClassEntity;
import ast.ObjectEntity;
import ast.Variable;
import base.ElException;
import processor.ProParameter;

public abstract class VMCommand extends VMBase {
  public int lineNum;
  
  public VMCommand create(ProParameter parameter) throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new ElException("Cannot create ", this);
    }
  }
  
  public abstract void execute() throws ElException;
  
  public void setGoto(int command) {
  }

  public ObjectEntity newObject(ClassEntity classEntity) throws ElException {
    ObjectEntity object = new ObjectEntity(classEntity);
    int index = -1;
    object.fields = new VMValue[classEntity.fields.size()];
    for(Variable parameter: classEntity.fields) {
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
