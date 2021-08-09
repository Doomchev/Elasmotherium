package ast;

import base.ElException;
import java.util.LinkedList;
import parser.Action;

public class Link extends Value {
  public static ID id = ID.get("link");
  
  public ID name;
  public Entity entity;
  public boolean thisFlag, isDefinition = false;
  public final LinkedList<Type> subtypes = new LinkedList<>();

  public Link(ID name) {
    this.name = name;
    Action.currentFlags.clear();
  }

  public Link(NamedEntity entity) {
    this.name = entity.name;
    this.entity = entity;
  }
  
  // processor fields
  
  @Override
  public ID getID() throws ElException {
    return name;
  }
  
  @Override
  public ID getObject() throws ElException {
    return id;
  }
  
  @Override
  public ClassEntity getType() throws ElException {
    return entity.getType();
  }

  @Override
  public void resolveTo(Entity entity) {
    this.entity = entity;
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

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    classEntity.parent = this;
  }
  
  // other
  
  @Override
  public String toString() {
    return name.string
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
