package vm;

public class Deallocate extends Command {
  int quantity;

  public Deallocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public Command execute() {
    stackPointer -= quantity;
    return nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
