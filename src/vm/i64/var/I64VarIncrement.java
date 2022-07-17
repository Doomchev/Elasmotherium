package vm.i64.var;

import ast.Entity;
import exception.ElException;
import processor.parameter.ProParameter;
import vm.VMCommand;

public class I64VarIncrement extends VMCommand {
  private final int index;

  public I64VarIncrement(int index, int proLine, Entity entity) {
    super(proLine, entity);
    this.index = index;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return new I64VarIncrement(parameter.getValue().getIndex(), proLine, entity);
  }
  
  @Override
  public void execute() {
    i64Stack[currentCall.varIndex(index)]++;
    currentCommand++;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + index;
  }
}
