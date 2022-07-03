package vm.texture;

import exception.ElException;
import vm.VMBase;
import vm.VMCommand;

public class TextureWidth extends VMCommand {
  @Override
  public void execute() throws ElException {
    i64Stack[stackPointer] = valueStack[stackPointer].getImage().getWidth();
    if(log) typeStack[stackPointer] = VMBase.ValueType.I64;
    currentCommand++;
  }
}
