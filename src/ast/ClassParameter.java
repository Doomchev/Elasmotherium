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
  public Entity getType() throws ElException {
    return ClassEntity.Int;
  }
  
  @Override
  public boolean isVariable(ID name)
      throws ElException {
    return this.name == name;
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addParameter(this);
  }
}
