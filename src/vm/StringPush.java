package vm;

public class StringPush extends Command {
  String value;

  public StringPush(String value) {
    this.value = value;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = value;
    typeStack[stackPointer] = TYPE_STRING;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " \"" + value + "\"";
  }
}
