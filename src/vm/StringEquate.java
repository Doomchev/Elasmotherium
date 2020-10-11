package vm;

public class StringEquate extends Command {
  int index;

  public StringEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stringStack[index + currentCall.paramPosition] = stringStack[stackPointer];
    typeStack[index + currentCall.paramPosition] = TYPE_I64;
    stackPointer--;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
