package ast.function;

import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import ast.IDEntity;
import base.ElException;
import base.EntityException;
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
  
  @Override
  public boolean isValue(ID name, boolean isThis) {
    return this.name == name && fromParametersQuantity == 0;
  }
  
  // processing

  @Override
  public void process(FunctionCall call) throws EntityException {
    if(log) println(subIndent + "Resolving method " + toString());
    call.resolveParameters(parameters);
    append();
  }

  @Override
  public void process(FunctionCall call, Entity[] subTypes) throws EntityException {
    if(log) println(subIndent + "Resolving method " + toString());
    call.resolveParameters(parameters, subTypes);
    append();
  }

  @Override
  public void resolve(Entity type) throws EntityException {
    append(new ObjectVarPush(currentFunction.allocation));
    resolveChild(type);
  }

  @Override
  public void resolveChild(Entity type) throws EntityException {
    if(log) println(subIndent + "Resolving method " + toString());
    append();
    try {
      convert(returnType.getNativeClass(), type.getNativeClass());
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    classEntity.addMethod(this);
  }
}
