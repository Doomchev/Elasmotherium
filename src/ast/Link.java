package ast;

import base.ElException;
import java.util.LinkedList;

public class Link extends Value {
  public static final ID id = ID.get("link");
  
  private final ID name;
  private final LinkedList<Link> subtypes = new LinkedList<>();

  public Link(ID name) {
    this.name = name;
  }
  
  // properties
  
  @Override
  public ID getName() throws ElException {
    return name;
  }

  void addSubType(Link type) {
    subtypes.add(type);
  }
  
  // processor fields
  
  @Override
  public ID getObject() throws ElException {
    return id;
  }
  
  // processing
  
  @Override
  public Entity resolve() throws ElException {
    Entity entity = getFromScope(name);
    if(entity == null)
      throw new ElException(name + " is not found.");
    ClassParameter parameter = entity.toClassParameter();
    if(parameter != null) return parameter;
    ClassEntity classEntity = entity.toClass();
    if(subtypes.isEmpty()) return classEntity;
    Type type = new Type(classEntity);
    type.setSubTypes(subtypes);
    return type;
  }
  
  // type conversion

  @Override
  public ClassEntity toClass() throws ElException {
    throw new ElException("Unresolved link " + toString());
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
  public void moveToFunction(Function function) {
    function.setReturnType(this);
  }
  
  // other
  
  @Override
  public String toString() {
    return "#" + name.string
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
