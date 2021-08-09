package vm;

public class VMAllocate extends VMCommand {
  int quantity;

  public VMAllocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public void execute() {
    if(currentCall.paramPosition < 0)
      currentCall.paramPosition = stackPointer + 1;
    stackPointer += quantity;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
