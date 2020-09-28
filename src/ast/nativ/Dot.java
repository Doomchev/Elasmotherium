package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import ast.FunctionCall;
import ast.NativeFunction;
import ast.Scope;
import ast.Variable;
import java.util.LinkedList;

public class Dot extends NativeFunction {
  public Dot() {
    super("dot");
  }
  
  @Override
  public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    Entity param0 = parameters.getFirst();
    param0.setTypes(parentScope);
    ClassEntity param0Class = param0.getType().toClass();
    Variable field = param0Class.getVariable(
        parameters.getLast().getNameID());
    parameters.set(1, field);
    field.setTypes(parentScope);
  }

  @Override
  public void setParameterTypes(LinkedList<Entity> parameters
      , Scope parentScope) {
  }

  @Override
  public void toByteCode(FunctionCall call) {
  }

  @Override
  public void objectToByteCode(FunctionCall call) {
    call.parameters.getFirst().objectToByteCode(null);
    Variable variable = call.parameters.getLast().toVariable();
    fieldIndex = variable.index;
    fieldType = variable.type.toClass();
  }
}
