package ast;

import base.ElException;
import java.util.LinkedList;
import parser.Action;

public class Link extends Value {
  public static final ID id = ID.get("link");
  
  private final ID name;
  private Entity entity;
  public final LinkedList<Type> subtypes = new LinkedList<>();

  public Link(ID name) {
    this.name = name;
    Action.currentFlags.clear();
  }

  public Link(NamedEntity entity) {
    this.name = entity.name;
    this.entity = entity;
  }
  
  // properties
  
  @Override
  public ID getName() throws ElException {
    return name;
  }

  void add(Type type) {
    subtypes.add(type);
  }
  
  // processor fields
  
  @Override
  public ID getObject() throws ElException {
    return id;
  }
  
  @Override
  public ClassEntity getType() throws ElException {
    return entity.getType();
  }
  
  // type conversion

  @Override
  public Variable toVariable() {
    return entity.toVariable();
  }

  @Override
  public Function toFunction() {
    return entity.toFunction();
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToLink(this);
  }
  
  // other
  
  @Override
  public String toString() {
    return "#" + name.string
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
