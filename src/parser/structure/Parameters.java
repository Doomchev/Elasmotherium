package parser.structure;

import java.util.LinkedList;

public class Parameters extends Value {
  public final LinkedList<Value> parameters = new LinkedList<>();
  
  @Override
  public ID getID() {
    return parametersID;
  }
  
  @Override
  public void move(Entity entity) {
    entity.moveToParameters(this);
  }

  @Override
  void moveToFormula(Formula formula) {
    formula.chunks.add(this);
  }

  @Override
  void moveToFunctionCall(FunctionCall call) {
    call.parameters.addAll(parameters);
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return parameters;
  }

  @Override
  public String toString() {
    return listToString(parameters);
  }
}
