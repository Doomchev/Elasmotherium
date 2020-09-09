package parser.structure;

import export.Chunk;
import java.util.LinkedList;
import java.util.ListIterator;
import static parser.structure.Entity.addCommand;
import vm.I64Allocate;

public class Function extends FlagEntity {
  public Code code = new Code();
  public Value formula;
  public Entity type = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isClassField = false;
  public Chunk form = null;
  public int i64quantity = -1;
  
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
    Scope scope = code.scope;
    scope = new Scope(parentScope);
    currentFunction = this;
    for(Variable variable : parameters) {
      scope.add(variable, variable.name);
      variable.setAllocation();
    }
    code.addToScope(null);
  }

  @Override
  public void setTypes(Scope parentScope) {
    code.setTypes(parentScope);
  }

  @Override
  public void setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    setTypes(parentScope);
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
  public void toByteCode() {
    if(i64quantity >= 0) addCommand(new I64Allocate(i64quantity + 1));
    code.toByteCode();
  }

  public void toByteCode(FunctionCall call) {
    ListIterator<Variable> iterator = parameters.listIterator();
    for(Entity parameter : call.parameters) {
      parameter.toByteCode();
      if(!iterator.hasNext()) error("Too many parameters in " + getName());
      conversion(parameter.getType(), iterator.next().type);
    }
    if(iterator.hasNext()) error("Too few parameters in " + getName());
    functionToByteCode(call);
  }
  
  @Override
  public void logScope(String indent) {
    if(code.scope != null) code.logScope(indent);
  }
}
 