package ast.function;

import ast.ClassEntity;
import ast.ID;
import base.ElException;
import vm.object.ObjectVarPush;

public class Method extends StaticFunction {
  public Method(ID name) {
    super(name);
  }

  public static CustomFunction createMethod(ID id) {
    return allocateFunction(new Method(id));
  }
  
  // properties

  @Override
  public int getCallDeallocation() {
    return parameters.size() + 1;
  }
  
  // processing

  @Override
  public void process(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving method " + toString());
    call.resolveParameters(parameters);
    append();
  }

  @Override
  public void resolve(ClassEntity parameter) throws ElException {
    if(log) println(subIndent + "Resolving method " + toString());
    append(new ObjectVarPush(currentFunction.allocation));
    append();
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    classEntity.addMethod(this);
  }
}
