package parser;

import base.Module;
import ast.Block;
import ast.Entity;
import ast.EntityStack;
import ast.Function;
import ast.FunctionCall;
import ast.ID;
import ast.Variable;
import base.ElException;

public class ActionCreate extends Action {
  private final EntityStack stack;
  private final Function function;
  private final ID type;

  public ActionCreate(EntityStack stack, ID type, Function function) {
    this.stack = stack;
    this.type = type;
    this.function = function;
  }
  
  @Override
  public void execute() throws ElException {
    currentAction = this;
    if(type == Entity.moduleID) {
      String name = EntityStack.id.pop().string;
      Module.current.modules.add(new Module(modulesPath + "/" + name + ".es"));
    } else if(stack.isStringBased()) {
      String string = prefix + text.substring(tokenStart, textPos);
      if(log) log("CREATE " + (type == null ? "" : type + " ")
          + stack.name.string + "(" + string + ")");
      stack.push(stack.create(string, type));
      prefix = "";
      tokenStart = textPos;
    } else if(type != null) {
      if(log) log("CREATE BLOCK " + type.string);
      stack.push(new Block(type));
    } else if(function != null) {
      if(log) log("CREATE FUNCTION CALL " + function.getName() + ")");
      stack.push(new FunctionCall(function));
    } else {
      stack.push(stack.create());
      if(log) log("CREATE " + stack.name.string + "(" + stack.peek() + ")");
    }
    currentAction = nextAction;
  }

  @Override
  public String toString() {
    return stack.name.string + (type == null ? "" : "-" + type);
  }
}
