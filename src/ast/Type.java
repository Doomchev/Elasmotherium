package ast;

import ast.function.Method;
import exception.EntityException;
import exception.NotFound;
import vm.values.VMValue;

import java.util.Arrays;

public class Type extends Entity {
  private final ClassEntity basicClass;
  private final Entity[] subTypes;

  public Type(ClassEntity basicClass, Entity[] subTypes) {
    super(0, 0);
    this.basicClass = basicClass;
    this.subTypes = subTypes;
  }
  
  // properties
  
  @Override
  public ClassEntity getNativeClass() throws EntityException {
    return basicClass.getNativeClass();
  }
  
  @Override
  public Entity getType() throws EntityException {
    return this;
  }

  public Entity getSubtype(int index) throws EntityException {
    return subTypes[index];
  }
  
  @Override
  public Entity getMethod(ID id, int parametersQuantity) throws NotFound {
    Method method = basicClass.getMethod(id, parametersQuantity);
    if(subTypes.length == 0) return method;
    return method;
  }

  public Entity resolveObject() throws EntityException {
    currentType = this;
    return this;
  }
  
  // other

  @Override
  public VMValue createValue() {
    if(basicClass.name.string.equals("Array")) return null;
    return basicClass.createValue();
  }

  @Override
  public String toString() {
    return basicClass.name
        + (subTypes.length == 0 ? "" : Arrays.toString(subTypes));
  }
}
