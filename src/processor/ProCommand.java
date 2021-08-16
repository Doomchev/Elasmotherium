package processor;

import base.ElException;
import static parser.ParserBase.subIndent;

public abstract class ProCommand extends ProBase {
  int lineNum;

  public ProCommand() {
    lineNum = currentLineNum;
  }

  ProCommand create(String param) throws ElException {
    try {
      return getClass().newInstance();
    } catch(InstantiationException | IllegalAccessException ex) {
      throw new ElException("Cannot create ", this);
    }
  }
  
  abstract void execute() throws ElException;
  
  public void log(String message) {
    System.out.println(subIndent + lineNum + ": " + message);
  }
}
