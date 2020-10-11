package vm;

public class StringStackPush extends Command {
  int index;

  public StringStackPush(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = stringStack[currentCall.paramPosition + index];
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
