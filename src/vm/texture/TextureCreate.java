package vm.texture;

import exception.ElException;
import vm.VMCommand;

public class TextureCreate extends VMCommand {
  @Override
  public void execute() throws ElException {
    valueStack[stackPointer] = new Texture(stringStack[stackPointer]);
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
}
