package ast.function;

import ast.ClassEntity;
import ast.Code;
import ast.Entity;
import ast.ID;
import ast.IDEntity;
import ast.Variable;
import exception.ElException;
import exception.EntityException;
import vm.VMCommand;
import vm.call.ReturnVoid;

public class StaticFunction extends CustomFunction {
  protected Entity returnType = null;
  
  // creating
  
  public StaticFunction() {
  }
  
  public StaticFunction(IDEntity name) {
    super(name);
  }

  public static CustomFunction createStaticFunction(IDEntity id) {
    return allocateFunction(new StaticFunction(id));
  }

  public void add(StaticFunction function) {
    code.add(function);
  }
  
  // properties
  
  @Override
  public Entity getType() throws EntityException {
    return returnType.getType();
  }
  
  @Override
  public ClassEntity getNativeClass() throws EntityException {
    return returnType.getNativeClass();
  }

  @Override
  public void setReturnType(Entity returnType) {
    this.returnType = returnType;
  }
  
  @Override
  public boolean isValue(ID name, boolean isThis) {
    return this.name == name && fromParametersQuantity == 0 && !isThis;
  }
  
  // preprocessing
  
  @Override
  public void resolveTypes() throws EntityException {
    addToScope(this);
    if(returnType != null) returnType = returnType.resolve();
    for(Variable parameter: parameters) addToScope(parameter);
    for(Variable parameter: parameters) parameter.resolveType();
  }
  
  // compiling

  @Override
  public void compileCall(FunctionCall call) throws EntityException {
    if(log) println(subIndent + "Calling static function " + this);
    call.resolveParameters(parameters);
    append();
  }

  @Override
  public void resolveTo(Entity type) throws EntityException {
    if(log) println(subIndent + "Resolving static function " + this
      + " without parameters");
    append();
  }

  @Override
  public void resolveCallTo(Entity type, FunctionCall call)
      throws EntityException {
    if(log) println(subIndent + "Resolving static function " + this);
    call.resolveParameters(parameters);
    append();
    try {
      convert(returnType.getNativeClass(), type.getNativeClass());
    } catch (ElException ex) {
      throw new EntityException(this, ex.message);
    }
  }
    
  public void append() throws EntityException {
    if(command != null) {
      try {
        append(command.create(0, this));
      } catch (ElException ex) {
        throw new EntityException(this, ex.message);
      }
    } else {
      append(new vm.call.CallFunction(this, 0, this));
    }
  }
  
  @Override
  public VMCommand getEndingCommand() {
    return new ReturnVoid(0, this);
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
