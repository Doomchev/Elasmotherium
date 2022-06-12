package ast;

import exception.ElException;
import exception.EntityException;
import java.util.LinkedList;
import java.util.ListIterator;

public class StringSequence extends Value {
  public final static ID id = ID.get("stringSequence");
  
  private final LinkedList<Entity> chunks = new LinkedList<>();

  public StringSequence() {
    super(0, 0);
  }
  
  // child objects

  void add(Value value) {
    chunks.add(value);
  }

  // properties
  
  @Override
  public ID getID() throws EntityException {
    return id;
  }

  // resolving

  public Entity resolveEntity() throws EntityException {
    ListIterator<Entity> it = chunks.listIterator();
    while(it.hasNext()) {
      Entity entity = it.next();
      it.set(entity.resolveEntity());
    }
    return this;
  }
  
  // compiling
  
  /*@Override
  public void resolve(Entity parameter) throws EntityException {
    boolean isNotFirst = false;
    for(Entity entity: chunks) {
      entity.resolve(ClassEntity.String);
      if(isNotFirst) {
        append(new vm.string.StringAdd());
      } else {
        isNotFirst = true;
      }
    }
  }*/
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToStringSequence(this);
  }
  
  // other

  @Override
  public String toString() {
    return listToString(chunks, " + ");
  }
}