package vm;

public class VMDeallocate extends VMCommand {
  int quantity;

  public VMDeallocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public void execute() {
    stackPointer -= quantity;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
