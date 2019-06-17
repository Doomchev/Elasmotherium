package parser.structure;

import java.util.HashMap;

public class Scope {
  public HashMap<ID, Entity> entries = new HashMap<>();
  public Scope parentScope;

  public Scope(Scope parentScope) {
    this.parentScope = parentScope;
  }

  public void add(NamedEntity entity) {
    add(entity, entity.name);
  }

  public void add(Entity entity, ID id) {
    if(!entries.containsKey(id)) entries.put(id, entity);
  }
  
  public Entity get(ID id, boolean thisFlag) {
    return thisFlag ? getClassField(id) : getVariable(id);
  }
  
  public Entity getVariable(ID id) {
    Entity entity = entries.get(id);
    if(entity != null && !entity.isClassField()) return entity;
    if(parentScope == null) return null;
    return parentScope.getVariable(id);
  }
  
  public Entity getClassField(ID id) {
    Entity entity = entries.get(id);
    if(entity != null && entity.isClassField()) return entity;
    if(parentScope == null) return null;
    return parentScope.getClassField(id);
  }

  public void log(String indent) {
    for(HashMap.Entry<ID, Entity> entry : entries.entrySet()) {
      Entity value = entry.getValue();
      Entity type = value.getType();
      System.out.println(indent + (type == null ? "? " : type.toString() + " ")
          + entry.getKey().string + ":");
      value.logScope(indent + "  ");
    }
  }
}
