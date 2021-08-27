package ast;

import base.ElException;
import java.util.LinkedList;
import vm.VMCommand;

public class Code extends Entity {
  private final LinkedList<Entity> lines = new LinkedList<>();
  private final LinkedList<Function> functions = new LinkedList<>();
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

  public void add(Function function) {
    functions.add(function);
  }

  public void add(ClassEntity classEntity) {
    classes.add(classEntity);
  }

  public void setFunctionCommand(ID id, VMCommand command) throws ElException {
    for(Function function: functions)
      if(function.getName() == id) {
        function.setCommand(command);
        return;
      }
    throw new ElException("Function " + id + " is not found.");
  }
  
  // processing
  
  @Override
  public void process() throws ElException {
    allocateScope();
    processWithoutScope(null);
    deallocateScope();
  }
  
  public void processWithoutScope(VMCommand endingCommand) throws ElException {
    for(ClassEntity classEntity: classes) addToScope(classEntity);
    for(Function function: functions) addToScope(function);
    
    for(Entity line: lines) line.process();
    if(endingCommand != null) append(endingCommand);
    
    for(ClassEntity classEntity: classes) classEntity.process();
    for(Function function: functions) function.process();
  }

  void processConstructors() throws ElException {
    for(ClassEntity classEntity: classes) classEntity.processConstructors();
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToCode(this);
  }

  @Override
  public void moveToFunction(Function function) {
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
  public void print(String indent, String prefix) {
    println(indent + prefix + "{");
    String indent2 = indent + "  ";
    for(ClassEntity classEntity : classes) classEntity.print(indent2, "");
    if(!classes.isEmpty() && !functions.isEmpty()) println("");
    for(Function function : functions) function.print(indent2, "");
    if(!functions.isEmpty() || !classes.isEmpty()
        && !lines.isEmpty()) println("");
    for(Entity line : lines) line.print(indent2, "");
    println(indent + "}");
  }
}
