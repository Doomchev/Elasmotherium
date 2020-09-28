package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.FunctionCall;
import ast.NativeFunction;
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
  public void toByteCode(FunctionCall call) {
    addCommand(new ObjectNew(objectClass));
  }
}
