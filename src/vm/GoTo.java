package vm;

import ast.Entity;
import exception.ElException;
import exception.EntityException;
import processor.parameter.ProParameter;

public class GoTo extends VMCommand {
  private int command;

  public GoTo(int proLine, Entity entity) {
    super(proLine, entity);
  }

  @Override
  public VMCommand create(ProParameter parameter, int proLine, Entity entity)
      throws ElException, EntityException {
    GoTo goTo = new GoTo(proLine, entity);
    parameter.addLabelCommand(goTo);
    return goTo;
  }
  
  @Override
  public void setPosition(int command) {
    this.command = command;
  }
  
  @Override
  public void execute() {
    currentCommand = command;
  }
  
  @Override
  public String toString() {
    return super.toString() + " " + command;
  }
}
