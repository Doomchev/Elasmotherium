package vm;

public class ObjectEquate extends Command {
  int index;

  public ObjectEquate(int index) {
    this.index = index;
  }
  
  @Override
  public Command execute() {
    int stackIndex = index + currentCall.paramPosition;
    objStack[stackIndex] = objStack[stackPointer];
    typeStack[stackIndex] = TYPE_OBJECT;
    stackPointer--;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
