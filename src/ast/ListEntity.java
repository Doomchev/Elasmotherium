package ast;

import base.ElException;
import java.util.LinkedList;

public class ListEntity extends Value {
  public final LinkedList<Value> values = new LinkedList<>();
  
  @Override
  public ID getID() {
    return listID;
  }

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToList(this);
  }

  @Override
  public String toString() {
    return values.toString();
  }
}
