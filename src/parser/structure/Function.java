package parser.structure;

import export.Chunk;
import java.util.LinkedList;
import java.util.ListIterator;
import static parser.structure.Entity.addCommand;
import parser.structure.Scope.ScopeEntry;
import vm.Call;
import vm.Command;
import vm.Allocate;
import vm.CallParam;
import vm.NewFunctionCall;
import vm.VMBase;

public class Function extends FlagEntity {
  public Code code = new Code();
  public Value formula;
  public Entity type = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isClassField = false, isNativeFunction = false;
  public Chunk form = null;
  public int varIndex = -1, paramIndex = -1;
  public Command startingCommand;
  
  public Function(ID name) {
    this.name = name;
    addFlags();
  }

  @Override
  boolean isClassField() {
    return isClassField;
  }
  
  @Override
  public ID getID() {
    return functionID;
  }

  @Override
  public Chunk getForm() {
    return form;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return parameters;
  }

  @Override
  public Entity getType() {
    return type;
  }

  @Override
  public Entity getChild(ID id) {
    if(id == typeID) return type;
    if(id == codeID) return code;
    if(id == formulaID) return formula;
    return null;
  }

  @Override
  public Function toFunction() {
    return this;
  }

  @Override
  public void addToScope(Scope parentScope) {
    parentScope.add(this);
    code.addToScope(parentScope);
    currentFunction = this;
    for(Variable variable : parameters) code.scope.add(variable, variable.name);
  }

  @Override
  public void setTypes(Scope parentScope) {
    if(type.toClass() == null) type.setTypes(parentScope);
    type = type.toClass();
    for(Variable variable : parameters) {
      if(variable.type.toClass() == null) variable.type.setTypes(parentScope);
      variable.type = type.toClass();
      Entity paramType = variable.type;
      if(paramType == i64Class) {
        paramIndex++;
        variable.index = paramIndex;
      }
    }
    for(Variable variable : parameters) variable.setTypes(parentScope);
    code.setTypes(parentScope);
  }

  @Override
  public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
  }

  @Override
  public void move(Entity entity) {
    entity.moveToFunction(this);
  }

  @Override
  void moveToClass(ClassEntity classEntity) {
    isClassField = true;
    classEntity.methods.add(this);
  }

  @Override
  void moveToCode(Code code) {
    code.lines.add(this);
  }
  
  @Override
  public void functionToByteCode() {
    VMBase.currentFunction = this;
    VMBase.currentCommand = null;
    if(varIndex >= 0) addCommand(new Allocate(varIndex + 1));
    code.toByteCode();
    for(ScopeEntry entry : code.scope.entries)
      entry.entity.functionToByteCode();
  }

  public void toByteCode(FunctionCall call) {
    if(!isNativeFunction) {
      addCommand(new NewFunctionCall());
      if(!call.parameters.isEmpty()) addCommand(new CallParam());
    }    
    ListIterator<Variable> iterator = parameters.listIterator();
    for(Entity parameter : call.parameters) {
      Entity type = parameter.getType();
      parameter.toByteCode();
      if(!iterator.hasNext()) error("Too many parameters in " + getName());
      conversion(type, iterator.next().type);
    }
    if(iterator.hasNext()) error("Too few parameters in " + getName());
    functionToByteCode(call);
    if(!isNativeFunction) addCommand(new Call(this));
    conversion(type, call.convertTo);
  }
  
  @Override
  public void logScope(String indent) {
    if(code.scope != null) code.logScope(indent);
  }
}
 