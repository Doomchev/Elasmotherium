package ast;

import base.ElException;
import java.util.LinkedList;
import vm.values.VMValue;

public class Type extends NamedEntity {
  private final LinkedList<Type> subtypes = new LinkedList<>();
  private ClassEntity typeClass = null;
  
  // creating
  
  public Type(ID name) {
    super(name);
  }

  public Type(String name) {
    super(name);
  }
  
  // processor fields
  
  @Override
  public ClassEntity getType() throws ElException {
    return getFromScope(name).toClass();
  }
  
  // type conversion

  @Override
  public ClassEntity toClass() throws ElException {
    if(typeClass == null) typeClass = ClassEntity.all.get(name);
    if(typeClass == null) throw new ElException("Cannot find class " + name);
    return typeClass;
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToType(this);
  }

  @Override
  public void moveToType(Type type) {
    type.subtypes.add(this);
  }

  @Override
  public void moveToVariable(Variable variable) {
    variable.setType(this);
  }

  @Override
  public void moveToFunction(Function function) {
    function.setReturnType(this);
  }

  @Override
  public void moveToLink(Link link) throws ElException {
    link.add(this);
  }

  @Override
  public VMValue createValue() throws ElException {
    return getType().createValue();
  }
  
  // other

  @Override
  public String toString() {
    return name.string
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
