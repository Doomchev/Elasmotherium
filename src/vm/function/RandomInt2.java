package vm.function;

import base.ElException;
import java.util.Random;
import vm.VMCommand;

public class RandomInt2 extends VMCommand {
  private static final Random random = new Random();
  
  @Override
  public void execute() throws ElException {
    stackPointer--;
    int from = (int) i64Stack[stackPointer];
    int until = (int) i64Stack[stackPointer + 1];
    i64Stack[stackPointer] = random.nextInt(until - from) + from;
    currentCommand++;
  }
}
