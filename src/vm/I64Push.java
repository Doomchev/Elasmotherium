package vm;

public class I64Push extends Command {
  long value;

  public I64Push(long value) {
    this.value = value;
  }
  
  @Override
  public Command execute() {
    i64StackPointer++;
    i64Stack[i64StackPointer] = value;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + value;
  }
}
