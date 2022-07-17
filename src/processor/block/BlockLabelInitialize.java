package processor.block;

import ast.ID;
import exception.ElException;
import processor.ProCommand;

public class BlockLabelInitialize extends ProCommand {
  ID name;

  public BlockLabelInitialize(ID name, int proLine) {
    super(proLine);
    this.name = name;
  }
  
  @Override
  public void execute() throws ElException {
    currentBlock.createLabel(name);
    if(log) log("initialize label " + name);
  }
}
