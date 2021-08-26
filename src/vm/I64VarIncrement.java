package vm;

public class I64VarIncrement extends VMCommand {
  private final int index;

  public I64VarIncrement(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    i64Stack[index + currentCall.paramPosition]++;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
