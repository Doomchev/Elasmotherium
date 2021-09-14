package ast.function;

import ast.ClassEntity;
import ast.ID;
import base.ElException;
import vm.object.ObjectThisPush;

public class Method extends StaticFunction {
  public Method(ID name) {
    super(name);
  }

  public static CustomFunction createMethod(ID id) {
    return allocateFunction(new Method(id));
  }

  @Override
  public int getCallDeallocation() {
    return parameters.size() + 1;
  }

  @Override
  public void call(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving method " + toString());
    append(new ObjectThisPush());
    resolveParameters(call);
    if(command != null) {
      append(command.create());
    } else {
      append(new vm.call.CallFunction(this));
    }
  }

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    classEntity.addMethod(this);
  }
}
