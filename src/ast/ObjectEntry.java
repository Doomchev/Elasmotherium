package ast;

public class ObjectEntry extends Entity {
  public ID key;
  public Value value;

  public ObjectEntry(ID key) {
    this.key = key;
  }
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToObjectEntry(this);
  }
}
