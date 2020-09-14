package vm;

public class I64Deallocate extends Command {
  int quantity;

  public I64Deallocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public Command execute() {
    i64StackPointer -= quantity;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
