package ast;

import exception.EntityException;
import static base.StringFunctions.listToString;
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
  
  @Override
  public Entity getType(Entity[] subTypes) throws EntityException {
    return resolve().getType(subTypes);
  }
  
  // processing
  
  @Override
  public Entity resolve() throws EntityException {
    return ClassEntity.current;
  }
  
  @Override
  public Entity resolveRecursively() throws EntityException {
    return resolve();
  }

  @Override
  public Entity getObject() throws EntityException {
    return resolve().getObject();
  }
  
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
