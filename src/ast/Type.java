package ast;

import base.ElException;
import java.util.LinkedList;
import vm.values.VMValue;

public class Type extends Entity {
  private final LinkedList<Entity> subtypes = new LinkedList<>();
  private final ClassEntity basicClass;

  public Type(ClassEntity basicClass) {
    this.basicClass = basicClass;
  }
  
  // properties

  public void setSubTypes(LinkedList<Link> subtypeLinks) throws ElException {
    if(!subtypes.isEmpty())
      throw new ElException("Subtypes are already resolved.");
    for(Link link: subtypeLinks)
      subtypes.add(link.resolve());
  }
  
  // processor fields
  
  @Override
  public Entity getType() throws ElException {
    return this;
  }
  
  // type conversion

  @Override
  public ClassEntity toClass() throws ElException {
    return basicClass;
  }
  
  @Override
  public ClassEntity toNativeClass() throws ElException {
    return basicClass.toNativeClass();
  }
  
  // moving functions

  @Override
  public VMValue createValue() throws ElException {
    return basicClass.createValue();
  }
  
  // other

  @Override
  public String toString() {
    return basicClass.name
        + (subtypes.isEmpty() ? "" : "<" + listToString(subtypes) + ">");
  }
}
