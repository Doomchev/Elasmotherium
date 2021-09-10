package ast.function;

import ast.ClassEntity;
import ast.ID;
import base.ElException;

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
  public void moveToClass(ClassEntity classEntity) throws ElException {
    classEntity.addMethod(this);
  }
}
