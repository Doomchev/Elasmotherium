package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import ast.NativeFunction;

public class Not extends NativeFunction {
  public Not() {
    super("not");
  }
  
  @Override
  public int getPriority() {
    return 16;
  }
  
  @Override
  public Entity calculateType(Entity type0, Entity type1) {
    return ClassEntity.booleanClass;
  }
}
