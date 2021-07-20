package ast;

import java.util.LinkedList;

public class StringSequence extends Value {
  public final LinkedList<Value> chunks = new LinkedList<>();
  
  @Override
  public ID getID() {
    return stringSequenceID;
  }
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToStringSequence(this);
  }

  @Override
  public String toString() {
    return listToString(chunks, " + ");
  }
}
