package ast.function;

import ast.ClassEntity;
import ast.Entity;
import static ast.Entity.append;
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
  
  // processor fields
  
  @Override
  public Entity getType() throws ElException {
    return parentClass;
  }
  
  // parameters

  @Override
  public int getCallDeallocation() {
    return parameters.size();
  }
  
  // processing
  
  @Override
  public void addToScope() {
    addToScope(paramName(parentClass.name, parameters.size()), this);
  }

  @Override
  public void call(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving constructor " + toString());
    
    if(command != null) {
      resolveParameters(call);
      append(command.create());
      return;
    }
    append(new vm.object.ObjectCreate(parentClass));
    resolveParameters(call);
    append(new vm.call.CallFunction(this));
  }

  @Override
  public void processConstructor(ClassEntity classEntity) throws ElException {
    for(Variable param: parameters)
      param.processField(classEntity, code);
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
    return "this";
  }
  
  @Override
  public void print(StringBuilder indent, String prefix) {
    print(indent, prefix, "create");
  }
}
