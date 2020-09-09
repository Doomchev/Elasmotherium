package parser.structure;

import export.Chunk;
import parser.Action;
import static parser.structure.Entity.addCommand;
import vm.I64StackPush;

public class Link extends Value {
  public ID name;
  public Variable variable;
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
    return variable.getType();
  }

  @Override
  public void addToScope(Scope scope) {
    if(variable != null) variable.addToScope(scope);
  }

  @Override
  void setFunction(FunctionCall call) {
    call.functionName = name;
    call.thisFlag = thisFlag;
  }
  
  @Override
  public void setTypes(Scope parentScope) {
    variable = parentScope.getVariable(name).toVariable();
    if(variable == null) error("Variable " + name + " not found.");
    variable.setTypes(parentScope);
  }
  
  @Override
  public void toByteCode() {
    Entity type = variable.type;
    int index = variable.index;
    if(type == ClassEntity.i64Class) {
      addCommand(new I64StackPush(index));
    } else {
      error(type.toString() + " variable link is not implemented.");
    }
    conversion(type, convertTo);
  }

  @Override
  public String toString() {
    return name.string;
  }
}
