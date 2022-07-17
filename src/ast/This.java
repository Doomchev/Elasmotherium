package ast;

import exception.EntityException;
import vm.VMFieldCommand;
import vm.object.ObjectVarPush;

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
  
  // compiling

  @Override
  public Entity resolve() throws EntityException {
    append(new ObjectVarPush(VMFieldCommand.OBJECT, 0, this));
    return ClassEntity.current;
  }

  @Override
  public void resolveTo(Entity type) throws EntityException {
    append(new ObjectVarPush(VMFieldCommand.OBJECT, 0, this));
  }

  @Override
  public Entity resolveLinks() throws EntityException {
    return resolve();
  }

  @Override
  public Entity resolveObject() throws EntityException {
    return resolve().resolveObject();
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
