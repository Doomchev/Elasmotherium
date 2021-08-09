package ast;

public abstract class NamedEntity extends Entity {
  public ID name;
  
  // other

  @Override
  public String toString() {
    return name.string;
  }
}
