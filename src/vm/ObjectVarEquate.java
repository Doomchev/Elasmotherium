package vm;

public class ObjectVarEquate extends VMCommand {
  int index;

  public ObjectVarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    int stackIndex = index + currentCall.paramPosition;
    objectStack[stackIndex] = objectStack[stackPointer];
    if(log) typeStack[stackIndex] = ValueType.OBJECT;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
