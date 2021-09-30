package ast;

import ast.function.Method;
import base.EntityException;
import base.EntityException.Cannot;
import java.util.Arrays;
import vm.values.VMValue;

public class Type extends Entity {
  private final ClassEntity basicClass;
  private final Entity[] subtypes;

  public Type(ClassEntity basicClass, Entity[] subtypes) {
    super(0, 0);
    this.basicClass = basicClass;
    this.subtypes = subtypes;
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
  
  @Override
  public Entity getMethod(ID id, int parametersQuantity) throws EntityException {
    Method method = basicClass.getMethod(id, parametersQuantity);
    if(subtypes.length == 0) return method;
    return new ParameterizedEntity(subtypes, method);
  }

  @Override
  public Entity[] getSubTypes(ID className, int quantity) throws EntityException {
    if(basicClass.name != className || quantity != subtypes.length)
      throw new Cannot("convert " + basicClass.name + " to ", this);
    return subtypes;
  }
  
  // other

  @Override
  public VMValue createValue() {
    return basicClass.createValue();
  }

  @Override
  public String toString() {
    return basicClass.name
        + (subtypes.length == 0 ? "" : Arrays.toString(subtypes));
  }
}
