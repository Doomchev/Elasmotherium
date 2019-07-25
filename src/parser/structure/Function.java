package parser.structure;

import export.Chunk;
import java.util.LinkedList;

public class Function extends FlagEntity {
  public Code code = new Code();
  public Value formula;
  public Entity type = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isClassField = false;
  public Chunk form = null;
  public Function next = null;
  
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
    for(Variable variable : parameters) variable.setTypes(parentScope);
    code.setTypes(parentScope);
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
