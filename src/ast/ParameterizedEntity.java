package ast;

import ast.function.FunctionCall;
import exception.EntityException;

public class ParameterizedEntity extends Entity {
  public final Entity[] subTypes;
  public final Entity entity;

  public ParameterizedEntity(Entity[] subTypes, Entity entity) {
    super(0, 0);
    this.subTypes = subTypes;
    this.entity = entity;
  }
  
  // processing

  @Override
  public void compileCall(FunctionCall call) throws EntityException {
    entity.compileCall(call, subTypes);
  }

  @Override
  public Entity resolve() throws EntityException {
    return subTypes[0];
  }
}
