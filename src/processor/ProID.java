package processor;

import ast.ID;
import base.ElException;
import vm.VMCommand;

public class ProID extends ProParameter {
  private final ID id;

  public ProID(String label) {
    this.id = ID.get(label);
  }
  
  @Override
  public void addLabelCommand(VMCommand command) throws ElException {
    ProLabel.addCommand(id, command);
  }
  
  @Override
  public ID getID() throws ElException {
    return id;
  }
}
