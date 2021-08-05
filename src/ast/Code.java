package ast;

import base.ElException;
import java.util.LinkedList;

public class Code extends Entity {
  public final LinkedList<Entity> lines = new LinkedList<>();
  public final LinkedList<Function> functions = new LinkedList<>();
  public final LinkedList<ClassEntity> classes = new LinkedList<>();
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
  public void move(Entity entity) throws ElException {
    entity.moveToCode(this);
  }

  @Override
  public void moveToFunction(Function function) {
    removeAllocation();
    function.code = this;
  }

  @Override
  public void moveToVariable(Variable variable) {
    removeAllocation();
    variable.code = this;
  }

  @Override
  public void moveToBlock() throws ElException {
    removeAllocation();
  }
  
  @Override
  public void process() throws ElException {
    pushScope();
    processWithoutScope();
    popScope();
  }
  
  public void processWithoutScope() throws ElException {
    for(Entity line: lines) line.process();
    for(ClassEntity classEntity : classes) classEntity.process();
    for(Function function : functions) function.process();
  }

  @Override
  public String toString() {
    return listToString(lines);
  }

  @Override
  public void print(String indent, String prefix) {
    println(indent + prefix + "{");
    String indent2 = indent + "  ";
    for(ClassEntity classEntity : classes) classEntity.print(indent2, "");
    for(Function function : functions) function.print(indent2, "");
    for(Entity line : lines) line.print(indent2, "");
    println(indent + "}");
  }
}
