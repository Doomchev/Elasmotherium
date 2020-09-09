package vm;

public class I64StackPush extends Command {
  int index;

  public I64StackPush(int index) {
    this.index = index;
  }
  
  @Override
  public Command execute() {
    i64StackPointer++;
    i64Stack[i64StackPointer] = i64Stack[currentCall.i64ParamPosition + index];
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
