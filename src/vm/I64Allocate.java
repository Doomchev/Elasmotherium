package vm;

public class I64Allocate extends Command {
  int quantity;

  public I64Allocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public Command execute() {
    currentCall.i64ParamPosition = i64StackPointer + 1;
    i64StackPointer += quantity;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
