package ast.function;

import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import ast.Type;
import ast.Variable;
import base.ElException;
import base.EntityException;

public class Constructor extends StaticFunction {
  protected ClassEntity parentClass = null;
  
  public Constructor() {
  }

  public static CustomFunction createConstructor() {
    return allocateFunction(new Constructor());
  }
  
  // properties
  
  @Override
  public Entity getType(Entity[] subTypes) throws EntityException {
    return new Type(parentClass, subTypes);
  }
  
  @Override
  public ClassEntity getNativeClass() throws EntityException {
    return parentClass;
  }

  @Override
  public int getCallDeallocation() {
    return parameters.size();
  }
  
  // preprocessing

  @Override
  public void processConstructor(ClassEntity classEntity)
      throws EntityException {
    for(Variable param: parameters)
      param.processField(classEntity, code);
  }
  
  // processing

  @Override
  public boolean isFunction(ID name, int parametersQuantity) {
    return parentClass.name == name
        && fromParametersQuantity <= parametersQuantity
        && parametersQuantity <= toParametersQuantity;
  }

  public boolean matches(int parametersQuantity) {
    return fromParametersQuantity <= parametersQuantity
        && parametersQuantity <= toParametersQuantity;
  }

  @Override
  public void resolve(Entity type, FunctionCall call)
      throws EntityException {
    if(log) println(subIndent + "Resolving constructor " + toString());
    
    try {
      if(command != null) {
        call.resolveParameters(parameters);
        append(command.create());
      } else {
        append(new vm.object.ObjectCreate(parentClass));
        call.resolveParameters(parameters);
        append(new vm.call.CallFunction(this));
      }
      convert(parentClass.getNativeClass(), type.getNativeClass());
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) {
    deallocateFunction();
    parentClass = classEntity;
    classEntity.addConstructor(this);
  }
  
  // other

  @Override
  public String toString() {
    return (parentClass == null ? "" : parentClass.name)
        + "(" + listToString(parameters) + ")";
  }
  
  @Override
  public void print(StringBuilder indent, String prefix) {
    print(indent, prefix, "create");
  }
}
