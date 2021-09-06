package ast;

import java.util.LinkedList;

public class Parameters extends Value {
  private final LinkedList<Value> parameters = new LinkedList<>();
  
  // parameters

  void add(Value value) {
    parameters.add(value);
  }
  
  // moving functions
  
  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToParameters(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.add(this);
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) {
    call.add(parameters);
    call.priority = VALUE;
  }
  
  // other

  @Override
  public String toString() {
    return "{" + listToString(parameters) + "}";
  }
}
