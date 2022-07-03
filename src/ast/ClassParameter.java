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
  public boolean isValue(ID name, boolean isThis) {
    return this.name == name && !isThis;
  }

  public Entity getType() throws EntityException {
    return currentType.getSubtype(index);
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    index = classEntity.addParameter(this);
  }
}
