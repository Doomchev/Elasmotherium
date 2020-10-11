package vm;

public class StringThisEquate extends Command {
  int index;

  public StringThisEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    currentCall.thisObject.fields[index].stringSet(stringStack[stackPointer]);
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
