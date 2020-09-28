package ast;

import java.util.LinkedList;
import vm.StringAdd;

public class StringSequence extends Value {
  public final LinkedList<Value> chunks = new LinkedList<>();
  
  @Override
  public ID getID() {
    return stringSequenceID;
  }

  @Override
  public Entity getType() {
    return ClassEntity.stringClass;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return chunks;
  }
  
  @Override
  public void setTypes(Scope parentScope) {
    for(Value value : chunks) {
      value.setTypes(parentScope);
      if(value.getType() != ClassEntity.stringClass)
        value.convertTo = ClassEntity.stringClass;
    }
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToStringSequence(this);
  }

  @Override
  public void toByteCode() {
    boolean notFirst = false;
    for(Value value : chunks) {
      value.toByteCode();
      if(notFirst) addCommand(new StringAdd());
      notFirst = true;
    }
  }

  @Override
  public String toString() {
    return listToString(chunks);
  }
}
