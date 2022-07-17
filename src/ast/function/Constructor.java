package ast.function;

import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import ast.Variable;
import exception.ElException;
import exception.EntityException;
import exception.NotFound;
import vm.collection.ArrayCreate;

public class Constructor extends StaticFunction {
  protected ClassEntity parentClass = null;
  
  public Constructor() {
  }

  public static CustomFunction createConstructor() {
    return allocateFunction(new Constructor());
  }
  
  // properties
  
  @Override
  public ClassEntity getNativeClass() throws EntityException {
    return parentClass;
  }

  @Override
  public boolean isConstructor() {
    return true;
  }
  
  // preprocessing

  @Override
  public void processConstructor(ClassEntity classEntity)
      throws NotFound {
    for(Variable param: parameters) {
      param.processField(classEntity, code);
    }
  }
  
  // compiling

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
  public void resolveCallTo(Entity type, FunctionCall call)
      throws EntityException {
    if(log) println(subIndent + "Resolving constructor " + this);
    
    try {
      Entity oldType = currentType;
      currentType = type;
      if(parentClass.name.string.equals("Array")) {
        call.resolveParameters(parameters);
        append(new ArrayCreate(type.getSubtype(0).getNativeClass(), 0, this));
      } else if(command == null) {
        append(new vm.object.ObjectCreate(parentClass, 0, this));
        call.resolveParameters(parameters);
        append(new vm.call.CallFunction(this, 0, this));
      } else {
        call.resolveParameters(parameters);
        append(command.create(0, this));
      }
      currentType = oldType;
      convert(parentClass.getNativeClass(), type.getNativeClass());
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }

  public Entity resolveFunction(int parametersQuantity) throws EntityException {
    return this;
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
