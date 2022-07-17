package vm.string;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringPush extends VMCommand {
  private final String value;

  public StringPush(String value, int proLine, Entity entity) {
    super(proLine, entity);
    this.value = value;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException, EntityException {
    return new StringPush(parameter.getValue().getStringValue(), proLine, entity);
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = value;
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " \"" + value + "\"";
  }
}
