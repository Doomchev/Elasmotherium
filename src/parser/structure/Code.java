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
  public void setTypes(Scope parentScope) {
    for(Entity entity : lines) entity.setTypes(scope);
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
  public void toByteCode() {
    for(Entity entity : lines) entity.toByteCode();
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
