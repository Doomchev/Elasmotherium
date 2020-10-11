package vm;

import ast.ClassEntity;

public class VMNewThis extends Command {
  ClassEntity classEntity;

  public VMNewThis(ClassEntity classEntity) {
    this.classEntity = classEntity;
  }
  
  @Override
  public void execute() {
    currentCall.thisObject = newObject(classEntity);
    stackPointer--;
    currentCommand = nextCommand;
  }  
}
