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
  
  // processing

  @Override
  public void process(FunctionCall call) throws ElException {
    if(log) println(subIndent + "Calling static function " + toString());
    call.resolveParameters(parameters);
    append();
  }

  @Override
  public void resolve(ClassEntity parameter) throws ElException {
    if(log) println(subIndent + "Resolving static function " + toString()
      + " without parameters");
    append();
  }

  @Override
  public void resolve(ClassEntity parameter, FunctionCall call) throws ElException {
    if(log) println(subIndent + "Resolving static function " + toString());
    call.resolveParameters(parameters);
    append();
    convert(returnType.getNativeClass(), parameter);
  }
    
  public void append() throws ElException {
    if(command != null) {
      append(command.create());
    } else {
      append(new vm.call.CallFunction(this));
    }
  }
  
  @Override
  public void resolveTypes() throws ElException {
    addToScope(this);
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
