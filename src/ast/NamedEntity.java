package ast;

import base.ElException.Cannot;

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
  
  public void addToScope() throws Cannot {
    throw new Cannot("add to scope", this);
  }
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
}
