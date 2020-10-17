package ast;

import java.util.LinkedList;

public class Code extends Entity {
  public final LinkedList<Entity> lines = new LinkedList<>();
  public final LinkedList<Function> functions = new LinkedList<>();
  public Code parent;
  
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
  public void move(Entity entity) {
    entity.moveToCode(this);
  }

  @Override
  public void moveToFunction(Function function) {
    function.code = this;
  }

  @Override
  public void moveToVariable(Variable variable) {
    variable.code = this;
  }

  @Override
  public void resolveLinks(Variables variables) {
    variables = new Variables(variables, this);
    for(Entity entity : lines) entity.resolveLinks(variables);
  }

  @Override
  public void toByteCode() {
    for(Entity entity : lines) entity.toByteCode();
  }

  @Override
  public String toString() {
    return listToString(lines);
  }
}
