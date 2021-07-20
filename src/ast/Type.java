package ast;

import base.ElException;
import java.util.LinkedList;

public class Type extends NamedEntity {
  public final LinkedList<Type> subtypes = new LinkedList<>();
  
  public ClassEntity typeClass = null;
  
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
  public ClassEntity toClass() throws ElException {
    if(typeClass == null) typeClass = ClassEntity.all.get(name);
    if(typeClass == null) throw new ElException("Cannot find class " + name);
    return typeClass;
  }

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToType(this);
  }

  @Override
  public void moveToType(Type type) {
    type.subtypes.add(this);
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
  void moveToLink(Link link) throws ElException {
    link.subtypes.add(this);
  }

  @Override
  public String toString() {
    return name.string
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
