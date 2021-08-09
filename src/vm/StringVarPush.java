package vm;

public class StringVarPush extends VMCommand {
  int index;

  public StringVarPush(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = stringStack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_STRING;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
