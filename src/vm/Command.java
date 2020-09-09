package vm;

public abstract class Command extends VMBase {
  public Command nextCommand;
  public int number;
  
  public abstract Command execute();
  
  public void setGoto(Command command) {
  }
  
  @Override
  public String toString() {
    return number + ": " + getClass().getSimpleName();
  }
}
