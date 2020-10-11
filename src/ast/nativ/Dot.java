package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import ast.FunctionCall;
import ast.ID;
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
    ID name = parameters.getLast().getNameID();
    Variable field = param0Class.getVariable(name);
    if(field == null) throw new Error("Field " + name + " is not found in "
        + param0.toString());
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
    ClassEntity fieldType = field.getType().toClass();
    int index = field.getIndex();
    if(fieldType == ClassEntity.stringClass) {
      addCommand(new StringFieldPush(index));
    } else if(fieldType == ClassEntity.i64Class) {
      addCommand(new I64FieldPush(index));
    } else {
      throw new Error("Getting " + fieldType.toString()
          + " field is not implemented.");
    }
  }
}
