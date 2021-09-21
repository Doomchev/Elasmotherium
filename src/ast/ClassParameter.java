package ast;

import base.ElException;

public class ClassParameter extends NamedEntity {
  private int index;
  
  // creating

  public ClassParameter(ID name) {
    super(name);
  }
  
  // properties
  
  @Override
  public Entity getType(Entity[] subTypes) throws ElException {
    return subTypes[index];
  }
  
  @Override
  public boolean isValue(ID name)
      throws ElException {
    return this.name == name;
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addParameter(this);
  }
}
