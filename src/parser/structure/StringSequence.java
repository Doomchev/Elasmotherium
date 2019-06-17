package parser.structure;

import java.util.LinkedList;

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
  public Entity setTypes(Scope parentScope) {
    return ClassEntity.stringClass;
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToStringSequence(this);
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return chunks;
  }

  @Override
  public String toString() {
    return listToString(chunks);
  }
}
