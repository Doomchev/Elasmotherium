package ast;

import ast.nativ.New;
import export.Chunk;
import parser.Action;
import vm.I64Equate;
import vm.I64Increment;
import vm.I64StackPush;
import vm.I64ThisEquate;
import vm.I64ThisIncrement;
import vm.ObjectStackPush;
import vm.StringEquate;
import vm.StringStackPush;
import vm.StringThisEquate;

public class Link extends Value {
  public ID name;
  public Variable variable;
  public Function function;
  public boolean thisFlag, isDefinition = false;
  
  public Link(ID name) {
    this.name = name;
    this.thisFlag = Action.currentFlags.contains(thisID);
    Action.currentFlags.clear();
  }

  public Link(Variable variable) {
    this.name = variable.name;
    this.variable = variable;
    this.thisFlag = variable.hasChild(thisID);
  }
  
  @Override
  public ID getID() {
    return linkID;
  }
  
  @Override
  public ID getNameID() {
    return name;
  }
  
  @Override
  public Chunk getForm() {
    return variable.getForm();
  }

  @Override
  public Chunk getCallForm() {
    return variable.getCallForm();
  }

  @Override
  public Entity getChild(ID id) {
    if(id == valueID) return variable;
    return variable.getChild(id);
  }

  @Override
  public boolean hasChild(ID id) {
    if(id == thisID) return thisFlag;
    if(id == definitionID) return isDefinition;
    return variable.hasChild(id);
  }

  @Override
  public Entity getType() {
    return variable == null ? function.getType() : variable.getType();
  }

  @Override
  public Variable toVariable() {
    return variable;
  }

  @Override
  public Function toFunction() {
    return function;
  }
  
  @Override
  public void setFlag(ID flag) {
    if(flag == ID.thisID) thisFlag = true;
  }
  
  
  
  @Override
  public void resolveLinks(Variables variables) {
    if(thisFlag) {
      variable = currentClass.getVariable(name);
    } else {
      variable = variables.get(name);
    }
  }

  @Override
  public void resolveLinks(FunctionCall call, Variables variables) {
    try {
      if(thisFlag) {
        function = currentClass.getMethod(name);
      } else {
        function = variables.getFunction(name);
      }
    } catch(Error error) {
      ClassEntity classEntity = ClassEntity.all.get(name);
      for(Function method : classEntity.methods) {
        if(!method.isConstructor) continue;
        if(method.parameters.size() == call.parameters.size()) {
          function = new New(classEntity, method);
          function.resolveLinks(call, variables);
          return;
        }
      }
      throw new Error("Cannot find function or class \"" + name + "\"");
    }
    function.resolveLinks(call, variables);
  }

  @Override
  public void resolveLinks(ClassEntity classEntity, Variables variables) {
    function = classEntity.getMethod(name);
  }
  
  @Override
  public void resolveEquationLinks(Variables variables) {
    try {
      variable = variables.get(name);
    } catch(Error error) {
      variable = new Variable(name);
      currentFunction.varIndex++;
      variable.index = currentFunction.varIndex;
    }
  }
  
  
  
  @Override
  public void toByteCode() {
    ClassEntity type = variable.type.toClass();
    int index = variable.index;
    if(type == ClassEntity.i64Class) {
      addCommand(new I64StackPush(index));
    } else if(type == ClassEntity.stringClass) {
      addCommand(new StringStackPush(index));
    } else if(type.isNative) {
      throw new Error(type.toString() + " variable link is not implemented.");
    } else {
      addCommand(new ObjectStackPush(index));
    }
    conversion(type, convertTo);
  }
  
  @Override
  public void toByteCode(FunctionCall call) {
    function.toByteCode(call);
  }

  @Override
  public void equationToByteCode() {
    ClassEntity type = variable.type.toClass();
    int index = variable.index;
    if(thisFlag) {
      if(type == ClassEntity.i64Class) {
        addCommand(new I64ThisEquate(index));
      } else if(type == ClassEntity.stringClass) {
        addCommand(new StringThisEquate(index));
      } else if(type.isNative) {
        throw new Error("Equate of " + type.toString()
            + " field is not implemented.");
      } else {
        throw new Error("Equate of object field is not implemented.");
      }
    } else {
      if(type == ClassEntity.i64Class) {
        addCommand(new I64Equate(index));
      } else if(type == ClassEntity.stringClass) {
        addCommand(new StringEquate(index));
      } else {
        throw new Error("Equate of " + type.toString()
            + " is not implemented.");
      }
    }
  }

  @Override
  public void incrementToByteCode() {
    ClassEntity type = variable.type.toClass();
    int index = variable.index;
    if(thisFlag) {
      if(type == ClassEntity.i64Class) {
        addCommand(new I64ThisIncrement(index));
      } else if(type.isNative) {
        throw new Error("Increment of " + type.toString()
            + " field is not implemented.");
      } else {
        throw new Error("Cannot increment object field.");
      }
    } else {
      if(type == ClassEntity.i64Class) {
        addCommand(new I64Increment(index));
      } else {
        throw new Error("Cannot increment object field.");
      }
    }
  }
  @Override
  public String toString() {
    return name.string;
  }
}
