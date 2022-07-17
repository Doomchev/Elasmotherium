package vm;

import ast.Entity;
import exception.ElException;
import exception.ElException.CannotCreate;
import exception.EntityException;
import processor.parameter.ProParameter;

import java.util.LinkedList;

public abstract class VMCommand extends VMBase {
  public Entity entity;
  public int proLine;

  public VMCommand() {
    this.proLine = 0;
    this.entity = null;
  }

  public VMCommand(int proLine, Entity entity) {
    this.proLine = proLine;
    this.entity = entity;
  }

  public VMCommand create(int proLine, Entity entity) throws ElException {
    try {
      VMCommand command = getClass().newInstance();
      command.proLine = proLine;
      command.entity = entity;
      return command;
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new CannotCreate(this);
    }
  }
  
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException {
    return create(proLine, entity);
  }
  
  public VMCommand create(LinkedList<ProParameter> parameters, int proLine
      , Entity entity) throws ElException, EntityException {
    throw new CannotCreate(this, toString() + " with "
        + parameters.size() + " parameters");
  }
  
  public abstract void execute() throws ElException;
  
  public void setPosition(int command) {
  }
}
