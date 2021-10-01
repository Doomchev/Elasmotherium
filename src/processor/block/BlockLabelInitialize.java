package processor.block;

import ast.ID;
import ast.exception.ElException;
import processor.ProCommand;

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
