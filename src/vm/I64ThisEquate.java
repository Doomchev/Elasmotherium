package vm;

public class I64ThisEquate extends Command {
  int index;

  public I64ThisEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    currentCall.thisObject.fields[index].i64Set(i64Stack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
