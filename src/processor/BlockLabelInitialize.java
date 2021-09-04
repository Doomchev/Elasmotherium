package processor;

import ast.ID;
import base.ElException;
import static processor.ProBase.currentBlock;

public class BlockLabelInitialize extends ProCommand {
  ID name;

  public BlockLabelInitialize(ID name) {
    this.name = name;
  }
  
  @Override
  public void execute() throws ElException {
    currentBlock.createLabel(name);
    if(log) log("initialize label " + name);
  }
}
