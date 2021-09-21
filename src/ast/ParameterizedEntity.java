package ast;

import ast.function.FunctionCall;
import base.ElException;

public class ParameterizedEntity extends Entity {
  public final Entity[] subTypes;
  public final Entity entity;

  public ParameterizedEntity(Entity[] subTypes, Entity entity) {
    this.subTypes = subTypes;
    this.entity = entity;
    Object a;
  }
  
  // processing

  @Override
  public void process(FunctionCall call) throws ElException {
    entity.process(call, subTypes);
  }
}
