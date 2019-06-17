package parser.structure;

import java.util.LinkedList;

public class Function extends FlagEntity {
  public Code code;
  public Value formula;
  public Entity type = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isClassField = false;
  
  public Function(ID name) {
    this.name = name;
    addFlags();
  }
  
  @Override
  public ID getID() {
    return functionID;
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
  public void addToScope(Scope parentScope) {
    parentScope.add(this);
    code.scope = new Scope(parentScope);
    for(Variable variable : parameters) code.scope.add(variable, variable.name);
    code.addToScope(null);
  }
  
  @Override
  public Entity setTypes(Scope parentScope) {
    if(type != null) return type;
    if(code == null) return ClassEntity.voidClass;
    type = ClassEntity.unknownClass;
    type = code.getReturnType(code.scope);
    return type;
  }

  @Override
  public Entity setCallTypes(LinkedList<Entity> parameters, Scope parentScope) {
    return setTypes(parentScope);
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
  public void logScope(String indent) {
    if(code != null) code.logScope(indent);
  }
}
