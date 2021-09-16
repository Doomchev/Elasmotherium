package ast.function;

import ast.ClassEntity;
import ast.Entity;
import ast.ID;
import ast.Variable;
import base.ElException;

public class Constructor extends StaticFunction {
  protected ClassEntity parentClass = null;
  
  public Constructor() {
    super(null);
  }

  public static CustomFunction createConstructor() {
    return allocateFunction(new Constructor());
  }
  
  // properties
  
  @Override
  public Entity getType() throws ElException {
    return parentClass;
  }
  
  @Override
  public ClassEntity getNativeClass() throws ElException {
    return parentClass;
  }

  @Override
  public int getCallDeallocation() {
    return parameters.size();
  }
  
  // preprocessing

  @Override
  public void processConstructor(ClassEntity classEntity) throws ElException {
    for(Variable param: parameters)
      param.processField(classEntity, code);
  }
  
  // processing

  @Override
  public boolean isFunction(ID name, int parametersQuantity) throws ElException {
    return parentClass.name == name
        && fromParametersQuantity <= parametersQuantity
        && parametersQuantity <= toParametersQuantity;
  }

  public boolean matches(int parametersQuantity) {
    return fromParametersQuantity <= parametersQuantity
        && parametersQuantity <= toParametersQuantity;
  }

  @Override
  public void resolve(ClassEntity parameter, FunctionCall call)
      throws ElException {
    if(log) println(subIndent + "Resolving constructor " + toString());
    
    if(command != null) {
      call.resolveParameters(parameters);
      append(command.create());
      return;
    }
    append(new vm.object.ObjectCreate(parentClass));
    call.resolveParameters(parameters);
    append(new vm.call.CallFunction(this));
    //convert(parentClass, parameter);
  }
  
  // moving functions

  @Override
  public void moveToClass(ClassEntity classEntity) throws ElException {
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
