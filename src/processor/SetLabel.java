package processor;

import ast.ID;
import base.ElException;
import vm.VMBase;

public class SetLabel extends ProCommand {
  ID label;

  public SetLabel(ID label) {
    this.label = label;
  }

  @Override
  public void execute() throws ElException {
    ProLabel.setPosition(label, VMBase.currentCommand + 1);
    if(log) log("set label " + label);
  }
}
