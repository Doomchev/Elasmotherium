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
    typeStack[stackIndex] = TYPE_OBJECT;
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
