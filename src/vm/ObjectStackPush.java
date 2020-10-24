package vm;

public class ObjectStackPush extends Command {
  int index;

  public ObjectStackPush(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    objectStack[stackPointer] = objectStack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_OBJECT;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
