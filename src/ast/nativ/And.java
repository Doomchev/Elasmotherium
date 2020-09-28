package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import ast.NativeFunction;

public class And extends NativeFunction {
  public And() {
    super("and");
  }
  
  @Override
  public int getPriority() {
    return 6;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    return ClassEntity.booleanClass;
  }
}
