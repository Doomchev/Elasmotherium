package ast;

import exception.EntityException;

public class ClassParameter extends NamedEntity {
  private int index;
  
  // creating

  public ClassParameter(IDEntity name) {
    super(name);
  }
  
  // properties
  
  @Override
  public Entity getType(Entity[] subTypes) throws EntityException {
    return subTypes[index];
  }
  
  @Override
  public boolean isValue(ID name, boolean isThis) {
    return this.name == name && !isThis;
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addParameter(this);
  }
}
