package ast.function;

import ast.ClassEntity;
import ast.Entity;
import ast.IDEntity;
import base.ElException;
import vm.object.ObjectVarPush;

public class Method extends StaticFunction {
  public Method(IDEntity name) {
    super(name);
  }

  public static CustomFunction createMethod(IDEntity id) {
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
  public void process(FunctionCall call, Entity[] subTypes) throws ElException {
    if(log) println(subIndent + "Resolving method " + toString());
    call.resolveParameters(parameters, subTypes);
    append();
  }

  @Override
  public void resolve(Entity type) throws ElException {
    append(new ObjectVarPush(currentFunction.allocation));
    resolveChild(type);
  }

  @Override
  public void resolveChild(Entity type) throws ElException {
    if(log) println(subIndent + "Resolving method " + toString());
    append();
    convert(returnType.getNativeClass(), type.getNativeClass());
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
    classEntity.addMethod(this);
  }
}
