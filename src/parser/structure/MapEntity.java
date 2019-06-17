package parser.structure;

import java.util.HashMap;
import java.util.LinkedList;

public class MapEntity extends Entity {
  public final LinkedList<MapEntry> entries = new LinkedList<>();
  
  @Override
  public ID getID() {
    return mapID;
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToMap(this);
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return entries;
  }
}
