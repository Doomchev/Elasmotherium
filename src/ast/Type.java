package ast;

import ast.function.Method;
import base.ElException;
import base.ElException.Cannot;
import java.util.Arrays;
import vm.values.VMValue;

public class Type extends Entity {
  private final ClassEntity basicClass;
  private final Entity[] subtypes;

  public Type(ClassEntity basicClass, Entity[] subtypes) {
    this.basicClass = basicClass;
    this.subtypes = subtypes;
  }
  
  // properties
  
  @Override
  public ClassEntity getNativeClass() throws ElException {
    return basicClass.getNativeClass();
  }
  
  @Override
  public Entity getType() throws ElException {
    return this;
  }
  
  @Override
  public Entity getMethod(ID id, int parametersQuantity) throws ElException {
    Method method = basicClass.getMethod(id, parametersQuantity);
    if(subtypes.length == 0) return method;
    return new ParameterizedEntity(subtypes, method);
  }

  @Override
  public Entity[] getSubTypes(ID className, int quantity) throws ElException {
    if(basicClass.name != className || quantity != subtypes.length)
      throw new Cannot("convert " + basicClass.name + " to ", this);
    return subtypes;
  }
  
  // other

  @Override
  public VMValue createValue() throws ElException {
    return basicClass.createValue();
  }

  @Override
  public String toString() {
    return basicClass.name
        + (subtypes.length == 0 ? "" : Arrays.toString(subtypes));
  }
}
