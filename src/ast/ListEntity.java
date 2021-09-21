package ast;

import base.ElException;
import java.util.LinkedList;

public class ListEntity extends Value {
  public static final ID id = ID.get("list");
  public static final ID classID = ID.get("List");
  
  public final LinkedList<Value> values = new LinkedList<>();
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToList(this);
  }
  
  // properties
  
  @Override
  public ID getID() throws ElException {
    return id;
  }
  
  // processing
  
  @Override
  public void resolve(Entity type) throws ElException {
    Entity elementType = type.getSubTypes(classID, 1)[0];
    append(new vm.collection.ListCreate());
    for(Value value: values) {
      value.resolve(elementType);
      append(new vm.i64.I64AddToList.I64AddToListNoDelete());
    }
  }
  
  // other

  @Override
  public String toString() {
    return values.toString();
  }
}
