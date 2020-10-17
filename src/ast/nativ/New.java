package ast.nativ;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.addCommand;
import ast.Function;
import ast.FunctionCall;
import ast.NativeFunction;
import ast.Variables;
import java.util.LinkedList;
import vm.ObjectNew;

public class New extends NativeFunction {
  ClassEntity objectClass;
  Function constructor;

  public New(ClassEntity objectClass, Function constructor) {
    super("new");
    this.objectClass = objectClass;
    this.constructor = constructor;
  }

  @Override
  public Entity getType() {
    return objectClass;
  }

  @Override
  public void resolveLinks(FunctionCall call, Variables variables) {
    call.type = objectClass;
  }

  @Override
  public void toByteCode(FunctionCall call) {
    
    if(constructor == null) {
      addCommand(new ObjectNew(objectClass));
    } else {
      constructor.toByteCode(call);
    }
  }
}
