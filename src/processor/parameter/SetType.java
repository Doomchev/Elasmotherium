package processor.parameter;

import exception.ElException;
import exception.EntityException;
import processor.ProCommand;

public class SetType extends ProCommand {
  public static final SetType instance = new SetType(null);
  
  private final ProParameter parameter;

  private SetType(ProParameter value) {
    this.parameter = value;
    this.line = currentLineReader.getLineNum();
  }

  @Override
  public ProCommand create(String param) throws ElException {
    return new SetType(ProParameter.get(param));
  }

  @Override
  public void execute() throws ElException, EntityException {
    currentType = parameter.getValue();
    if(log) log("Set current type to " + currentType);
  }
}
