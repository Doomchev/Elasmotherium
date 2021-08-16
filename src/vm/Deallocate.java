package vm;

public class Deallocate extends VMCommand {
  int quantity;

  public Deallocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public void execute() {
    stackPointer -= quantity;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
