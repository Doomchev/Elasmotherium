package ast;

import base.ElException;
import java.util.LinkedList;
import processor.Processor;

public class ListEntity extends Value {
  public static final ID id = ID.get("list");
  
  public final LinkedList<Value> values = new LinkedList<>();
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToList(this);
  }
  
  // processor fields
  
  @Override
  public ID getID() throws ElException {
    return id;
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    append(new vm.collection.ListCreate());
    for(Value value: values) {
      currentProcessor.resolve(value, ClassEntity.Int);
      append(new vm.i64.I64AddToList.I64AddToListNoDelete());
    }
  }
  
  // other

  @Override
  public String toString() {
    return values.toString();
  }
}
