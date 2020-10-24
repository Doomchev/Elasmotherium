package vm;

public class I64ThisIncrement extends Command {
  int index;

  public I64ThisIncrement(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    currentCall.thisObject.fields[index].increment();
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
