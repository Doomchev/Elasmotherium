package vm;

import ast.Entity;
import base.ElException;

public class StringPush extends VMCommand {
  String value;

  public StringPush(String value) {
    this.value = value;
  }
  
  @Override
  public StringPush create(Entity entity) throws ElException {
    return new StringPush(entity.getStringValue());
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = value;
    typeStack[stackPointer] = TYPE_STRING;
    currentCommand = nextCommand;
  }
  
  @Override
  public String toString() {
    return super.toString() + " \"" + value + "\"";
  }
}
