package ast;

import base.ElException;

public class ObjectEntry extends Entity {
  public ID key;
  public Value value;

  public ObjectEntry(IDEntity key) {
    super(key);
    this.key = key.value;
  }
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToObjectEntry(this);
  }
}
