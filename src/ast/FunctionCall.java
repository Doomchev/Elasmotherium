package ast;

import base.ElException;
import java.util.LinkedList;
import processor.ProBase;
import vm.NewFunctionCall;
import vm.VMBase;
import vm.VMCommand;

public class FunctionCall extends Value {
  public static ID id = ID.get("call");
  public static ID resolve = ID.get("resolve");
  
  public Function function;
  public Entity type;
  public ID functionID;
  public boolean thisFlag;
  public byte priority;
  public final LinkedList<Entity> parameters = new LinkedList<>();

  public FunctionCall(Function function) {
    this.function = function;
    this.priority = function == null ? 17 : function.priority;
  }
  
  @Override
  public byte getPriority() {
    return priority;
  }
  
  // processor fields
  
  @Override
  public ID getID() throws ElException {
    return functionID;
  }
  
  @Override
  public Entity getParameter(int index) throws ElException {
    if(index >= parameters.size()) throw new ElException("Parameter number"
        + index + " is not found.");
    return parameters.get(index);
  }
  
  @Override
  public ID getObject() throws ElException {
    resolveID();
    return function.getObject();
  }
  
  @Override
  public ClassEntity getType() throws ElException {
    return function.getType();
  }
  
  public void resolveID() throws ElException {
    if(function == null) {
      function = Function.all.get(functionID);
      if(function == null) {
        function = getFromScope(functionID).toFunction();
        if(function == null)
          throw new ElException("Function not found", function);
      }
    }
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    resolveID();
    resolveAll();
  }

  @Override
  public void resolveTo(Entity entity) throws ElException {
    function = entity.toFunction();
  }
  
  @Override
  public void resolveAll() throws ElException {
    if(log) println(toString());
    if(!function.isNative()) {
      int i = 0;
      for(Entity parameter: parameters) {
        currentProcessor.call(parameter, resolve
            , function.getParameter(i).getType());
        i++;
      }
    }
    VMCommand command = function.command;
    if(command != null) {
      append(command.create(null));
      setReturnType(function.returnType);
    } else if(function == Function.ret) {
      currentProcessor.call(this);
    } else {
      append(new NewFunctionCall(function));
      setReturnType(function.returnType);
      //ProBase.convert(function.returnType.getType());
    }
  }

  // type conversion
  
  @Override
  public FunctionCall toCall() {
    return this;
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws base.ElException {
    entity.moveToFunctionCall(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
  }

  // other
  
  @Override
  public String toString() {
    return (functionID == null ? function : functionID)
        + "(" + listToString(parameters) + ")";
  }
}
