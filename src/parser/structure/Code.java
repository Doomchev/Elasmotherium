package parser.structure;

import java.util.LinkedList;

public class Code extends Entity {
  public final LinkedList<Entity> lines = new LinkedList<>();
  public Scope scope;

  public Code() {
  }

  public Code(FunctionCall call) {
    lines.add(call);
  }
  
  @Override
  public ID getID() {
    return codeID;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return lines;
  }

  @Override
  public Entity getReturnType(Scope parentScope) {
    for(Entity entity : lines) {
      Entity type = entity.getReturnType(parentScope);
      if(type != null) return type;
    }
    return ClassEntity.voidClass;
  }

  @Override
  public Entity setTypes(Scope parentScope) {
    for(Entity entity : lines) entity.setTypes(scope);
    return null;
  }

  @Override
  public void move(Entity entity) {
    entity.moveToCode(this);
  }

  @Override
  void moveToFunction(Function function) {
    function.code = this;
  }

  @Override
  void moveToVariable(Variable variable) {
    variable.code = this;
  }

  @Override
  public void addToScope(Scope parentScope) {
    if(parentScope != null) scope = new Scope(parentScope);
    for(Entity entity : lines) entity.addToScope(scope);
  }
  
  @Override
  public void logScope(String indent) {
    scope.log(indent);
    for(Entity entity : lines) entity.logScope(indent);
  }

  @Override
  public String toString() {
    return listToString(lines);
  }
}
