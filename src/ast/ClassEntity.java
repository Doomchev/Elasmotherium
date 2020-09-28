package ast;

import java.util.HashMap;
import java.util.LinkedList;

public class ClassEntity extends FlagEntity {
  public static HashMap<ID, ClassEntity> all = new HashMap<>();
  
  public Type parent;
  public LinkedList<Variable> fields = new LinkedList<>();
  public LinkedList<Variable> parameters = new LinkedList<>();
  public LinkedList<Function> methods = new LinkedList<>();
  public Scope scope;
  public boolean isNative, processed = false;

  public ClassEntity(ID name) {
    this.name = name;
    this.isNative = flags.contains(ID.nativeID);
    all.put(name, this);
  }
  
  public ClassEntity(ID name, boolean add) {
    this.name = name;
    this.isNative = true;
    if(add) all.put(name, this);
  }
  
  
  @Override
  public ID getID() {
    return classID;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    LinkedList<Entity> list = new LinkedList<>();
    list.addAll(fields);
    list.addAll(methods);
    return list;
  }

  public Variable getVariable(ID id) {
    for(Variable child : fields)
      if(child.name == id) return child;
    return null;
  }

  @Override
  public Entity getChild(ID id) {
    if(id == parentID) return parent;
    return null;
  }

  @Override
  public ClassEntity getType() {
    return ClassEntity.classClass;
  }

  @Override
  public Entity getFieldType(ID fieldName) {
    for(Scope.ScopeEntry entry : scope.entries)
      if(entry.id == fieldName && !entry.entity.isClassField()) {
        entry.entity.setTypes(scope);
        return entry.entity.getType();
      }
    return null;
  }

  public Entity getFieldType(ID fieldName, Type type) {
    return getFieldType(fieldName);
  }

  @Override
  public Scope getScope() {
    return scope;
  }

  @Override
  public ClassEntity toClass() {
    return this;
  }

  @Override
  public void move(Entity entity) {
    entity.moveToClass(this);
  }

  @Override
  public void addToScope(Scope parentScope) {
    scope = new Scope(parentScope);
    for(Function method : methods) {
      if(!method.hasFlag(constructorID)) {
        scope.add(method);
        method.addToScope(scope);
      }
    }
    int index = -1;
    for(Variable variableBase : fields) {
      scope.add(variableBase);
      index++;
      variableBase.index = index;
    }
  }
  
  @Override
  public void logScope(String indent) {
    scope.log(indent);
  }
}
