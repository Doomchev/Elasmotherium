package vm;

import ast.Entity;
import exception.ElException;
import exception.ElException.CannotCreate;
import exception.EntityException;
import processor.parameter.ProParameter;

import java.util.LinkedList;

public abstract class VMCommand extends VMBase {
  public Entity entity = null;
  public int proLine = 0;

  public VMCommand() {
    if(currentLineReader != null) {
      this.entity = currentEntity;
      this.proLine = currentProLine;
    }
  }

  public VMCommand create() throws ElException {
    try {
      VMCommand command = getClass().newInstance();
      command.entity = currentEntity;
      command.proLine = currentLineReader.getLineNum();
      return command;
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new CannotCreate(this);
    }
  }
  
  public VMCommand create(ProParameter parameter)
      throws EntityException, ElException {
    return create();
  }
  
  public VMCommand create(LinkedList<ProParameter> parameters)
      throws ElException, EntityException {
    throw new CannotCreate(this, toString() + " with "
        + parameters.size() + " parameters");
  }
  
  public abstract void execute() throws ElException;
  
  public void setPosition(int command) {
  }
}
