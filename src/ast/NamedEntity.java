package ast;

import base.ElException;

public abstract class NamedEntity extends Entity {
  public final ID name;

  // creating
  
  public NamedEntity(ID name) {
    this.name = name;
  }
  
  public NamedEntity(String name) {
    this.name = ID.get(name);
  }
  
  // properties

  @Override
  public ID getName() {
    return name;
  }

  public boolean isFunction(ID name, int parametersQuantity)
      throws ElException {
    return false;
  }
  
  public boolean isValue(ID name)
      throws ElException {
    return false;
  }
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
}
