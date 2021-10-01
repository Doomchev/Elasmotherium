package processor;

import ast.exception.ElException;
import ast.exception.ElException.CannotCreate;
import ast.exception.EntityException;
import vm.VMBase;
import vm.VMCommand;

public abstract class ProCommand extends ProBase {
  public ProCommand create(String param) throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new CannotCreate(this, toString());
    }
  }
  
  public abstract void execute() throws ElException, EntityException;
  
  void append(VMCommand command) {
    if(log) log(command.toString());
    VMBase.append(command);
  }
  
  public void log(String message) {
    currentLineReader.log(message);
  }
}
