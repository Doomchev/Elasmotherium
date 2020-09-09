package vm;

public class I64Equate extends Command {
  int index;

  public I64Equate(int index) {
    this.index = index;
  }
  
  @Override
  public Command execute() {
    i64Stack[index] = i64Stack[i64StackPointer];
    i64StackPointer--;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
