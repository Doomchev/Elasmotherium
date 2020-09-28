package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import ast.FunctionCall;
import ast.NativeFunction;
import ast.Scope;
import ast.Variable;
import java.util.LinkedList;
import vm.I64FieldPush;
import vm.StringFieldPush;

public class Dot extends NativeFunction {
  public Dot() {
    super("dot");
  }
  
  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    Entity param0 = parameters.getFirst();
    param0.setTypes(parentScope);
    ClassEntity param0Class = param0.getType().toClass();
    Variable field = param0Class.getVariable(
        parameters.getLast().getNameID());
    parameters.set(1, field);
    field.setTypes(parentScope);
    return field.getType();
  }

  @Override
  public void setParameterTypes(LinkedList<Entity> parameters
      , Scope parentScope) {
  }

  @Override
  public void toByteCode(FunctionCall call) {
    call.parameters.getFirst().toByteCode();
    Entity field = call.parameters.getLast();
    ClassEntity type = field.getType().toClass();
    int index = field.getIndex();
    if(type == ClassEntity.stringClass) {
      addCommand(new StringFieldPush(index));
    } else if(type == ClassEntity.i64Class) {
      addCommand(new I64FieldPush(index));
    } else {
      error("Getting " + type.toString() + " field is not implemented.");
    }
  }

  @Override
  public void objectToByteCode(FunctionCall call) {
    call.parameters.getFirst().objectToByteCode(null);
    Variable variable = call.parameters.getLast().toVariable();
    fieldIndex = variable.index;
    fieldType = variable.type.toClass();
  }
}
