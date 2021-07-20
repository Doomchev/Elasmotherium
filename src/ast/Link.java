package ast;

import base.ElException;
import java.util.LinkedList;
import parser.Action;

public class Link extends Value {
  public ID name;
  public Variable variable;
  public Function function;
  public boolean thisFlag, isDefinition = false;
  public final LinkedList<Type> subtypes = new LinkedList<>();

  public Link(ID name) {
    this.name = name;
    this.thisFlag = Action.currentFlags.contains(thisID);
    Action.currentFlags.clear();
  }

  public Link(Variable variable) {
    this.name = variable.name;
    this.variable = variable;
  }
  
  @Override
  public ID getID() {
    return linkID;
  }

  @Override
  public Variable toVariable() {
    return variable;
  }

  @Override
  public Function toFunction() {
    return function;
  }

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToLink(this);
  }
  
  

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    classEntity.parent = this;
  }
  
  
  
  @Override
  public String toString() {
    return name.string
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
