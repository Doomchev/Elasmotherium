package vm;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.ProBase;
import processor.parameter.ProParameter;

public abstract class VMFieldCommand extends VMCommand {
  public static final int OBJECT = -1, LAST = -2;
  
  protected final int fieldIndex, varIndex;

  public VMFieldCommand(int fieldIndex, int varIndex, int proLine, Entity entity) {
    super(proLine, entity);
    this.fieldIndex = fieldIndex;
    this.varIndex = varIndex;
  }
  
  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException, EntityException {
    return create(ProBase.currentObject.getIndex(), parameter.getIndex()
        , proLine, entity);
  }
      
  public abstract VMCommand create(int fieldIndex, int varIndex, int proLine
      , Entity entity) throws ElException;
  
  @Override
  public String toString() {
    return super.toString() + " " + fieldIndex;
  }
}
