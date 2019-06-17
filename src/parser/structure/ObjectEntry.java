package parser.structure;

public class ObjectEntry extends Entity {
  public ID key;
  public Value value;
  
  @Override
  public ID getID() {
    return objectID;
  }

  public ObjectEntry(ID key) {
    this.key = key;
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToObjectEntry(this);
  }
}
