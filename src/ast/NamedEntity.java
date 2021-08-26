package ast;

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
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
}
