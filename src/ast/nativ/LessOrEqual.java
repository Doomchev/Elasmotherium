package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import ast.NativeFunction;

public class LessOrEqual extends NativeFunction {
  public LessOrEqual() {
    super("lessOrEqual");
  }
  
  @Override
  public int getPriority() {
    return 7;
  }
  
  @Override
  public Entity calculateType(Entity param0, Entity param1) {
    getPriorityType(param0, param1, NUMBER);
    return ClassEntity.booleanClass;
  }

  @Override
  public String getActionName() {
    return "compared";
  }
}
