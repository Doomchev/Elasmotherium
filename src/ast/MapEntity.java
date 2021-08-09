package ast;

import base.SimpleMap;

public class MapEntity extends Entity {
  public final SimpleMap<Value, Value> entries = new SimpleMap<>();
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToMap(this);
  }
}
