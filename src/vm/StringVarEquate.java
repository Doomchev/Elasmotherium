package vm;

public class StringVarEquate extends VMCommand {
  int index;

  public StringVarEquate(int index) {
    this.index = index;
  }
  
  @Override
  public void execute() {
    stringStack[index + currentCall.paramPosition] = stringStack[stackPointer];
    if(log) typeStack[index + currentCall.paramPosition] = ValueType.I64;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
