package vm;

public class StringPush extends Command {
  String value;

  public StringPush(String value) {
    this.value = value;
  }
  
  @Override
  public Command execute() {
    stackPointer++;
    stringStack[stackPointer] = value;
    typeStack[stackPointer] = TYPE_STRING;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " \"" + value + "\"";
  }
}
