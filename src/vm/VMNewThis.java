package vm;

import ast.ClassEntity;

public class VMNewThis extends VMCommand {
  ClassEntity classEntity;

  public VMNewThis(ClassEntity classEntity) {
    this.classEntity = classEntity;
  }
  
  @Override
  public void execute() {
    currentCall.thisObject = newObject(classEntity);
    currentCommand = nextCommand;
  }  
}
