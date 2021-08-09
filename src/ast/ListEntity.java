package ast;

import base.ElException;
import java.util.LinkedList;

public class ListEntity extends Value {
  public final LinkedList<Value> values = new LinkedList<>();
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToList(this);
  }
  
  // other

  @Override
  public String toString() {
    return values.toString();
  }
}
