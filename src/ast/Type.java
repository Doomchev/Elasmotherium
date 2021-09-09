package ast;

import ast.function.FunctionCall;
import base.ElException;
import java.util.Arrays;
import java.util.LinkedList;
import vm.values.VMValue;

public class Type extends Entity {
  private final ClassEntity basicClass;
  private final Entity[] subtypes;

  public Type(ClassEntity basicClass, Entity[] subtypes) {
    this.basicClass = basicClass;
    this.subtypes = subtypes;
  }
  
  // processor fields
  
  @Override
  public Entity getType() throws ElException {
    return basicClass;
  }
  
  @Override
  public ClassEntity getNativeClass() throws ElException {
    return basicClass.getNativeClass();
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
        + (subtypes.length == 0 ? "" : "<" + Arrays.toString(subtypes) + ">");
  }
}
