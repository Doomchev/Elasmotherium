package ast;

import ast.function.CustomFunction;
import ast.function.FunctionCall;
import ast.function.StaticFunction;
import base.ElException;
import java.util.LinkedList;
import vm.VMCommand;

public class Code extends Entity {
  private final LinkedList<Entity> lines = new LinkedList<>();
  private final LinkedList<StaticFunction> functions = new LinkedList<>();
  private final LinkedList<ClassEntity> classes = new LinkedList<>();
  
  // creating
  
  public Code() {
  }

  public Code(FunctionCall call) {
    lines.add(call);
  }
  
  // child objects

  public void addLine(Entity codeLine) {
    lines.add(codeLine);
  }
  
  public void addLineFirst(Entity codeLine) {
    lines.addFirst(codeLine);
  }

  public void add(StaticFunction function) {
    functions.add(function);
  }

  public void add(ClassEntity classEntity) {
    classes.add(classEntity);
  }
  
  public StaticFunction getFunction(ID id, int parametersQuantity)
      throws ElException {
    for(StaticFunction function: functions)
      if(function.isFunction(id, parametersQuantity)) return function;
    throw new ElException.NotFound(this, "Function " + id);
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    allocateScope();
    processWithoutScope(null);
    deallocateScope();
  }
  
  public void processWithoutScope(VMCommand endingCommand) throws ElException {
    for(ClassEntity classEntity: classes) classEntity.addToScope();
    for(StaticFunction function: functions) {
      addToScope(function);
      function.resolveTypes();
    }
    
    for(ClassEntity classEntity: classes) classEntity.resolveTypes();
    
    for(Entity line: lines) line.process();
    if(endingCommand != null) append(endingCommand);
    
    for(ClassEntity classEntity: classes) classEntity.process();
    for(StaticFunction function: functions) function.process();
  }

  public void processConstructors() throws ElException {
    for(ClassEntity classEntity: classes) classEntity.processConstructors();
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToCode(this);
  }

  public static Code create() {
    allocate();
    return new Code();
  }

  @Override
  public void moveToFunction(CustomFunction function) {
    function.setCode(this);
    deallocate();
  }

  @Override
  public void moveToBlock() throws ElException {
    deallocate();
  }
  
  // other

  @Override
  public String toString() {
    return listToString(lines);
  }

  @Override
  public void print(StringBuilder indent, String prefix) {
    println(indent + prefix + "{");
    indent.append("  ");
    for(ClassEntity classEntity : classes) classEntity.print(indent, "");
    if(!classes.isEmpty() && !functions.isEmpty()) println("");
    for(StaticFunction function : functions) function.print(indent, "");
    if(!functions.isEmpty() || !classes.isEmpty()
        && !lines.isEmpty()) println("");
    for(Entity line : lines) line.print(indent, "");
    indent.delete(0, 2);
    println(indent + "}");
  }
}
