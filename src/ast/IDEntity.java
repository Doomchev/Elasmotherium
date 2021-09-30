package ast;

public class IDEntity extends Entity {
  public final ID value;

  public IDEntity(ID value, int textStart, int textEnd) {
    super(textStart, textEnd);
    this.value = value;
  }
}
