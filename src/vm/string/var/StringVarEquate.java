package vm.string.var;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class StringVarEquate extends VMCommand {
  private final int index;

  public StringVarEquate(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException, EntityException {
    return new StringVarEquate(parameter.getValue().getIndex(), proLine, entity);
  }
  
  @Override
  public void execute() {
    stringStack[currentCall.varIndex(index)] = stringStack[stackPointer];
    if(log) typeStack[currentCall.varIndex(index)] = ValueType.STRING;
    stackPointer--;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
