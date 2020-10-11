package vm;

public class I64StackPush extends Command {
  int index;

  public I64StackPush(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = i64Stack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
