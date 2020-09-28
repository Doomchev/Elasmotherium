package vm;

public class ObjectFieldEquate extends Command {
  int stackIndex, fieldIndex;

  public ObjectFieldEquate(int stackIndex, int fieldIndex) {
    this.stackIndex = stackIndex;
    this.fieldIndex = fieldIndex;
  }
  
  @Override
  public Command execute() {
    //objectStack[stackIndex + currentCall.paramPosition]
    //    .objectSet(objectStack[stackPointer]);
    stackPointer--;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + stackIndex + " " + fieldIndex;
  }
}
