package vm;

public class I64StackPush extends Command {
  int index;

  public I64StackPush(int index) {
    this.index = index;
  }
  
  @Override
  public Command execute() {
    stackPointer++;
    i64Stack[stackPointer] = i64Stack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_I64;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
