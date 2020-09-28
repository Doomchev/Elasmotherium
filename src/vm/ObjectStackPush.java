package vm;

public class ObjectStackPush extends Command {
  int index;

  public ObjectStackPush(int index) {
    this.index = index;
  }
  
  @Override
  public Command execute() {
    stackPointer++;
    objStack[stackPointer] = objStack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_OBJECT;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
