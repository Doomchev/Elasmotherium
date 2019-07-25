package parser.structure;

import export.Chunk;
import java.util.LinkedList;
import parser.Action;

public class Link extends Value {
  public ID name;
  public Entity entity;
  public boolean thisFlag, isDefinition = false;
  
  public Link(ID name) {
    this.name = name;
    this.thisFlag = Action.currentFlags.contains(thisID);
    Action.currentFlags.clear();
  }

  public Link(Variable variable) {
    this.name = variable.name;
    this.entity = variable;
    this.thisFlag = variable.hasChild(thisID);
  }
  
  @Override
  public ID getID() {
    return linkID;
  }
  
  @Override
  public ID getNameID() {
    return name;
  }

  @Override
  public Chunk getForm() {
    return entity.getForm();
  }

  @Override
  public Chunk getCallForm() {
    return entity.getCallForm();
  }

  @Override
  public Entity getChild(ID id) {
    if(id == valueID) return entity;
    return entity.getChild(id);
  }

  @Override
  public boolean hasChild(ID id) {
    if(id == thisID) return thisFlag;
    if(id == definitionID) return isDefinition;
    return entity.hasChild(id);
  }

  @Override
  public Entity getType() {
    return entity.getType();
  }

  @Override
  Entity setTypes(Scope parentScope, boolean isClassField) {
    if(entity == null) {
      entity = parentScope.get(name, isClassField);
      if(entity == null) error(name + " is not found");
    }
    return entity.setTypes(parentScope);
  }

  @Override
  public Entity setTypes(Scope parentScope) {
    return setTypes(parentScope, thisFlag);
  }

  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    setTypes(parentScope);
    return entity.setCallTypes(parameters, parentScope);
  }

  @Override
  public Variable createVariable(Scope parentScope) {
    entity = parentScope.get(name, thisFlag);
    if(entity != null) return null;
    Variable variable = new Variable(name);
    entity = variable;
    parentScope.entries.put(name, variable);
    if(!thisFlag) isDefinition = true;
    return variable;
  }

  @Override
  public String toString() {
    if(entity == null) return name.string;
    return entity.toString();
  }
}
