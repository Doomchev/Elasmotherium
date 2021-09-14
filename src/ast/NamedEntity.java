package ast;

import base.ElException;
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

  public boolean matches(ID name, int parametersQuantity) throws ElException {
    return this.name == name;
  }
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
}
