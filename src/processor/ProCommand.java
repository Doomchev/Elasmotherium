package processor;

import exception.ElException;
import exception.ElException.CannotCreate;
import exception.EntityException;
import vm.VMBase;
import vm.VMCommand;

public abstract class ProCommand extends ProBase {
  public int line;

  public ProCommand create(String param) throws ElException {
    try {
      ProCommand command = getClass().newInstance();
      command.line = currentLineReader.getLineNum();
      return command;
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new CannotCreate(this, toString());
    }
  }
  
  public abstract void execute() throws ElException, EntityException;
  
  void append(VMCommand command, int proLine) {
    if(log) log(command.toString());
    VMBase.append(command, proLine);
  }
  
  public void log(String message) {
    currentLineReader.log(message, line);
  }
}
