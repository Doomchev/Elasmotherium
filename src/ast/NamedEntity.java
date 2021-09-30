package ast;

import base.ElException;

public abstract class NamedEntity extends Entity {
  public final ID name;

  // creating

  public NamedEntity(ID name, int textStart, int textEnd) {
    super(textStart, textEnd);
    this.name = name;
  }

  public NamedEntity(String name, int textStart, int textEnd) {
    super(textStart, textEnd);
    this.name = ID.get(name);
  }

  public NamedEntity(IDEntity id) {
    super(id);
    this.name = id.value;
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
