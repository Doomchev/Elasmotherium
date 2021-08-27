package vm;

public class Allocate extends VMCommand {
  private final int quantity;

  public Allocate(int quantity) {
    this.quantity = quantity;
  }
  
  @Override
  public void execute() {
    stackPointer += quantity;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + quantity;
  }
}
