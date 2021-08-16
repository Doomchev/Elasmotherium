package vm;

public class ObjectStackPush extends VMCommand {
  int index;

  public ObjectStackPush(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    objectStack[stackPointer] = objectStack[currentCall.paramPosition + index];
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
