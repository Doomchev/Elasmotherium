package ast;

import base.LinkedMap;

public class MapEntity extends Entity {
  public final LinkedMap<Value, Value> entries = new LinkedMap<>();
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToMap(this);
  }
}
