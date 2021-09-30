package ast;

import base.EntityException;

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
  public boolean isValue(ID name) {
    return this.name == name;
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addParameter(this);
  }
}
