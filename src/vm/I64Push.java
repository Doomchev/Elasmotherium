package vm;

public class I64Push extends Command {
  long value;

  public I64Push(long value) {
    this.value = value;
  }
  
  @Override
  public void execute() {
    stackPointer++;
    i64Stack[stackPointer] = value;
    typeStack[stackPointer] = TYPE_I64;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + value;
  }
}
