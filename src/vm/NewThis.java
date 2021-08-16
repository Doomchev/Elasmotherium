package vm;

import ast.ClassEntity;

public class NewThis extends VMCommand {
  ClassEntity classEntity;

  public NewThis(ClassEntity classEntity) {
    this.classEntity = classEntity;
  }
  
  @Override
  public void execute() {
    currentCall.thisObject = newObject(classEntity);
    currentCommand++;
  }  
}
