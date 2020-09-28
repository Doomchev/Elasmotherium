package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
import ast.Scope;
import java.util.LinkedList;
import vm.ObjectNew;

public class New extends NativeFunction {
  ClassEntity objectClass;

  public New(ClassEntity objectClass) {
    super("new");
    this.objectClass = objectClass;
  }

  @Override
  public Entity getType() {
    return objectClass;
  }

  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    return objectClass;
  }

  @Override
  public void toByteCode(FunctionCall call) {
    addCommand(new ObjectNew(objectClass));
  }
}
