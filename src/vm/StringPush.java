package vm;

public class StringPush extends Command {
  String value;

  public StringPush(String value) {
    this.value = value;
  }
  
  @Override
  public Command execute() {
    stringStackPointer++;
    stringStack[stringStackPointer] = value;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + value;
  }
}
