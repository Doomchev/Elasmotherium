package ast;

import java.util.HashMap;
import java.util.LinkedList;

public class Scope {
  public LinkedList<ScopeEntry> entries = new LinkedList<>();
  public Scope parentScope;

  public Scope(Scope parentScope) {
    this.parentScope = parentScope;
  }
  
  public static class ScopeEntry {
    ID id;
    Entity entity;

    public ScopeEntry(ID id, Entity entity) {
      this.id = id;
      this.entity = entity;
    }

    @Override
    public String toString() {
      return id.string + ": " + entity.toString();
    }
  }

  public void add(NamedEntity entity) {
    add(entity, entity.name);
  }

  public void add(Entity entity, ID id) {
    entries.add(new ScopeEntry(id, entity));
  }
  
  public Entity get(ID id, boolean thisFlag) {
    return thisFlag ? getClassField(id) : getVariable(id);
  }
  
  public Entity getVariable(ID id) {
    for(ScopeEntry entry : entries)
      if(entry.id == id && !entry.entity.isClassField()) return entry.entity;
    if(parentScope == null) return null;
    return parentScope.getVariable(id);
  }
  
  public Entity getClassField(ID id) {
    for(ScopeEntry entry : entries)
      if(entry.id == id && entry.entity.isClassField()) return entry.entity;
    if(parentScope == null) return null;
    return parentScope.getClassField(id);
  }

  public void log(String indent) {
    for(ScopeEntry entry : entries) {
      Entity value = entry.entity;
      Entity type = value.getType();
      System.out.println(indent + (type == null ? "? " : type.toString() + " ")
          + entry.id.string + ":");
      value.logScope(indent + "  ");
    }
  }
}
