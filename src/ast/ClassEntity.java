package ast;

import java.util.HashMap;
import java.util.LinkedList;

public class ClassEntity extends NamedEntity {
  public static HashMap<ID, ClassEntity> all = new HashMap<>();
  
  public Type parent;
  public LinkedList<Variable> fields = new LinkedList<>();
  public LinkedList<Variable> parameters = new LinkedList<>();
  public LinkedList<Function> methods = new LinkedList<>();
  public boolean isNative;

  public ClassEntity(ID name) {
    this.name = name;
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
    for(Variable field : fields)
      if(field.name == id) return field;
    return null;
  }

  public Function getMethod(ID name) {
    for(Function function : methods)
      if(function.name == name) return function;
    throw new Error("Method \"" + name + "\" is not found.");
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
  public void setFlag(ID flag) {
    if(flag == ID.nativeID) isNative = true;
  }
  
  @Override
  public ClassEntity toClass() {
    return this;
  }

  @Override
  public void resolveLinks(Variables variables) {
    currentClass = this;
    int index = -1;
    for(Variable field : fields) {
      index++;
      field.index = index;
      field.type = field.type.toClass();
    }
    for(Function method : methods) method.resolveLinks(variables);
  }

  @Override
  public void move(Entity entity) {
    entity.moveToClass(this);
  }
  
  @Override
  public void print(String indent) {
    System.out.println(indent + "class " + name);
    for(Variable field : fields) field.print(indent + " ");
    for(Function method : methods) method.print(indent + " ");
  }
}
