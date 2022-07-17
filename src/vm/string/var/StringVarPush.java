package vm.string.var;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringVarPush extends VMCommand {
  private final int index;

  public StringVarPush(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new StringVarPush(parameter.getIndex(), proLine, entity);
  }
  
  @Override
  public void execute() {
    stackPointer++;
    stringStack[stackPointer] = stringStack[currentCall.varIndex(index)];
    if(log) typeStack[stackPointer] = ValueType.STRING;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
