package vm;

public class ObjectEquate extends Command {
  int index;

  public ObjectEquate(int index) {
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
