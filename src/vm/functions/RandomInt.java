package vm.functions;

import base.ElException;
import java.util.Random;
import vm.VMCommand;

public class RandomInt extends VMCommand {
  private static final Random random = new Random();
  
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = random.nextInt((int) i64Stack[stackPointer]);
    currentCommand++;
  }
}
