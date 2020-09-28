package ast;

public class ObjectEntity extends Entity {
  public Entity type;
  public Entity[] fields;

  public ObjectEntity(Entity type) {
    this.type = type;
  }

  @Override
  public ID getID() {
    return objectID;
  }
}
