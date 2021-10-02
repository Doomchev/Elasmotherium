package ast;

import ast.function.CustomFunction;
import exception.ElException;
import exception.EntityException;
import exception.NotFound;
import java.util.LinkedList;

public class Link extends Value {
  public static final ID id = ID.get("link");
  
  private final ID name;
  private final LinkedList<Link> subTypes = new LinkedList<>();
  private final boolean isThis;

  public Link(IDEntity id, boolean isThis) {
    super(id);
    this.name = id.value;
    this.isThis = isThis;
  }
  
  // child objects

  void addSubType(Link type) {
    subTypes.add(type);
  }
  
  // properties
  
  @Override
  public ID getName() throws EntityException {
    return name;
  }
  
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
    try {
      if(subTypes.isEmpty()) return getVariableFromScope(name, isThis);
      ClassEntity basicClass = getClassFromScope(name);
      Entity[] resolvedTypes = new Entity[subTypes.size()];
      int index = 0;
      for(Link subType: subTypes) {
        resolvedTypes[index] = subType.resolve();
        index++;
      }
      return new Type(basicClass, resolvedTypes);
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  @Override
  public Entity resolveFunction(int parametersQuantity)
      throws EntityException {
    try {
      return getFunctionFromScope(name, parametersQuantity);
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
}

  @Override
  public void resolve(Entity type) throws EntityException {
    try {
      getVariableFromScope(name, isThis).resolve(type);
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  @Override
  public Entity resolveRecursively() throws EntityException {
    return resolve();
  }

  @Override
  public Entity resolveRecursively(int parametersQuantity) throws EntityException {
    return resolveFunction(parametersQuantity);
  }

  @Override
  public Entity getObject() throws EntityException {
    return resolve().getObject();
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToLink(this);
  }

  @Override
  public void moveToLink(Link link) throws ElException {
    link.addSubType(this);
  }

  @Override
  public void moveToVariable(Variable variable) {
    variable.setType(this);
  }

  @Override
  public void moveToFunction(CustomFunction function) throws ElException {
    try {
      function.setReturnType(this);
    } catch (EntityException ex) {
      throw new ElException(this, ex.message);
    } 
  }
  
  // other
  
  @Override
  public String toString() {
    return "#" + (isThis ? "this." : "") + name.string
        + (subTypes.isEmpty() ? "" : "<" + listToString(subTypes) + ">");
  }

  @Override
  public String toString(LinkedList<Entity> parameters) {
    return name + "(" + listToString(parameters) + ")";
  }
}
