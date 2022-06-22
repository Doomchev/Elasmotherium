package ast;

import ast.function.Method;
import exception.EntityException;
import exception.EntityException.Cannot;
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

  public Entity getSubType() throws EntityException {
    return subTypes[0];
  }
  
  @Override
  public Entity getMethod(ID id, int parametersQuantity) throws NotFound {
    Method method = basicClass.getMethod(id, parametersQuantity);
    if(subTypes.length == 0) return method;
    return new ParameterizedEntity(subTypes, method);
  }

  @Override
  public Entity[] getSubTypes(ID className, int quantity) throws EntityException {
    if(basicClass.name != className || quantity != subTypes.length)
      throw new Cannot("convert " + basicClass.name + " to ", this);
    return subTypes;
  }
  
  // other

  @Override
  public VMValue createValue() {
    return basicClass.createValue();
  }

  @Override
  public String toString() {
    return basicClass.name
        + (subTypes.length == 0 ? "" : Arrays.toString(subTypes));
  }
}
