package ast;

import exception.ElException;
import base.LinkedMap;

public class MapEntity extends Entity {
  public final LinkedMap<Value, Value> entries = new LinkedMap<>();

  public MapEntity() {
    super(0, 0);
  }
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToMap(this);
  }
}
