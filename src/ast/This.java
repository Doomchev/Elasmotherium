package ast;

import exception.EntityException;

import java.util.LinkedList;

public class This extends Value {
  public static final ID id = ID.get("this");
  
  public This() {
    super(currentSymbolReader.getTextPos() - 4
        , currentSymbolReader.getTextPos());
  }
  
  // properties
  
  @Override
  public ID getID() throws EntityException {
    return id;
  }

  // resolving

  @Override
  public Entity resolve() {
    return ClassEntity.current;
  }
  
  /*@Override
  public Entity getType(Entity[] subTypes) throws EntityException {
    return resolve().getType(subTypes);
  }
  
  // compiling

  @Override
  public Entity getObject() throws EntityException {
    return resolveEntity().getObject();
  }*/
  
  // other
  
  @Override
  public String toString() {
    return "this";
  }

  @Override
  public String toString(LinkedList<Entity> parameters) {
    return "this(" + listToString(parameters) + ")";
  }
}
