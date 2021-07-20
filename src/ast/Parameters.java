package ast;

import java.util.LinkedList;

public class Parameters extends Value {
  public final LinkedList<Value> parameters = new LinkedList<>();
  
  @Override
  public ID getID() {
    return parametersID;
  }
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToParameters(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.chunks.add(this);
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) {
    call.parameters.addAll(parameters);
    call.priority = VALUE;
  }

  @Override
  public String toString() {
    return listToString(parameters);
  }
}
