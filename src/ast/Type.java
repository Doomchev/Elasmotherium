package ast;

import java.util.LinkedList;

public class Type extends NamedEntity {
  public final LinkedList<Type> subtypes = new LinkedList<>();
  
  public ClassEntity typeClass;
  
  public Type(ID name) {
    this.name = name;
  }

  public Type(String id) {
    this.name = ID.get(id);
  }
  
  @Override
  public ID getID() {
    return typeID;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return subtypes;
  }

  @Override
  Entity getFieldType(ID fieldName) {
    typeClass.getFieldType(fieldName, this);
    return null;
  }

  @Override
  public ClassEntity toClass() {
    return typeClass;
  }

  @Override
  public void setTypes(Scope parentScope) {
    typeClass = parentScope.getVariable(name).toClass();
    for(Type subtype : subtypes) subtype.setTypes(parentScope);
  }

  @Override
  public void move(Entity entity) {
    entity.moveToType(this);
  }

  @Override
  public void moveToType(Type type) {
    subtypes.add(type);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    classEntity.parent = this;
  }

  @Override
  public void moveToVariable(Variable variable) {
    variable.type = this;
  }

  @Override
  public void moveToFunction(Function function) {
    function.type = this;
  }

  @Override
  public String toString() {
    if(typeClass == null) return name.string;
    return typeClass.name.string;
  }
}
