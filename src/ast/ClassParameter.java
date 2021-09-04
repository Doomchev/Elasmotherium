package ast;

import base.ElException;

public class ClassParameter extends NamedEntity {
  private int index;
  
  // creating

  public ClassParameter(ID name) {
    super(name);
  }
  
  // processor fields
  
  @Override
  public Entity getType() throws ElException {
    return ClassEntity.Int;
  }
  
  // type conversion

  @Override
  public ClassParameter toClassParameter() {
    return this;
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addParameter(this);
  }
}
