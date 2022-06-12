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
  
  // resolving

  @Override
  public Entity resolveEntity() throws EntityException {
    try {
      return getEntityFromScope(name, isThis).resolveEntity();
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  @Override
  public Entity resolveType() throws EntityException {
    try {
      if(subTypes.isEmpty()) return getEntityFromScope(name, isThis);
      ClassEntity basicClass = getClassFromScope(name);
      Entity[] resolvedTypes = new Entity[subTypes.size()];
      int index = 0;
      for(Link subType: subTypes) {
        resolvedTypes[index] = subType.resolveType();
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
      return getFunctionFromScope(name, parametersQuantity)
          .resolveFunction(parametersQuantity);
    } catch (NotFound ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToLink(this);
  }

  @Override
  public void moveToLink(Link link) {
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
