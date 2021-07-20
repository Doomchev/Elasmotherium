package ast;

public abstract class NamedEntity extends Entity {
  public ID name;

  @Override
  public String toString() {
    return name.string;
  }
}
