package vm;

public class I64Increment extends Command {
  int index;

  public I64Increment(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    i64Stack[index + currentCall.paramPosition]++;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
