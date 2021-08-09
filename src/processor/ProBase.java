package processor;

import ast.Entity;
import base.Base;
import static base.Base.subIndent;
import vm.VMBase;
import vm.VMCommand;

public class ProBase extends Base {
  static Entity parent, current;
  
  public void append(VMCommand command) {
    VMBase.append(command);
    System.out.println(subIndent + command.toString());
  }
}
