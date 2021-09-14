package vm.texture;

import base.ElException;
import vm.VMCommand;

public class TextureCreate extends VMCommand {
  @Override
  public void execute() throws ElException {
    objectStack[stackPointer] = new Texture(stringStack[stackPointer]);
    if(log) typeStack[stackPointer] = ValueType.OBJECT;
    currentCommand++;
  }
}
