package vm;

public class Allocate extends VMCommand {
  int quantity;

  public Allocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public void execute() {
    if(currentCall.paramPosition < 0)
      currentCall.paramPosition = stackPointer + 1;
    stackPointer += quantity;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
