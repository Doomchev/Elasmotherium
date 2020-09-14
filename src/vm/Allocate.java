package vm;

public class Allocate extends Command {
  int quantity;

  public Allocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public Command execute() {
    if(currentCall.paramPosition < 0)
      currentCall.paramPosition = stackPointer + 1;
    stackPointer += quantity;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
