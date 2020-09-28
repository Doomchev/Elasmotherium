package ast;

import java.util.LinkedList;

public class ListEntity extends Entity {
  public final LinkedList<Value> values = new LinkedList<>();
  
  @Override
  public ID getID() {
    return listID;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return values;
  }
}
