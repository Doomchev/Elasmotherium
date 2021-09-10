package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import ast.ID;
import ast.Variable;
import base.ElException;
import vm.VMCommand;

public class StaticFunction extends CustomFunction {
  protected Entity returnType = null;
  
  // constructors
  
  public StaticFunction(ID name) {
    super(name);
  }

  public static CustomFunction createStaticFunction(ID id) {
    return allocateFunction(new StaticFunction(id));
  }
  
  // parameters
  
  @Override
  public Entity getType() throws ElException {
    return returnType.getType();
  }

  @Override
  public void setReturnType(Entity returnType) {
    this.returnType = returnType;
  }
  
  @Override
  public void resolveTypes() throws ElException {
    if(returnType != null) returnType = returnType.resolve();
    for(Variable param: parameters) param.resolveType();
  }
  
  @Override
  public VMCommand getEndingCommand() throws ElException {
    return new vm.call.Return();
  }
  
  // moving functions

  @Override
  public void moveToCode(Code code) {
    deallocateFunction();
    code.add(this);
  }
  
  // other
  
  @Override
  public void print(StringBuilder indent, String prefix) {
    if(returnType != null) prefix += returnType + " ";
    print(indent, prefix, name.string);
  }
}
