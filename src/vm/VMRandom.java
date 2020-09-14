package vm;

import java.util.Random;

public class VMRandom extends Command {
  public static Random random = new Random();
  
  @Override
  public Command execute() {
    i64Stack[stackPointer] = random.nextInt((int) i64Stack[stackPointer]);
    return nextCommand;
  }
}
