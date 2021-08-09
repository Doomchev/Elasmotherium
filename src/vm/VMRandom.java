package vm;

import java.util.Random;

public class VMRandom extends VMCommand {
  public static Random random = new Random();
  
  @Override
  public void execute() {
    i64Stack[stackPointer] = random.nextInt((int) i64Stack[stackPointer]);
    currentCommand = nextCommand;
  }
}
