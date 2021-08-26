package parser;

import base.Module;
import ast.Block;
import ast.Function;
import ast.FunctionCall;
import ast.ID;
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
  public ActionCreate create(String params) throws ElException {
    String[] param = params.split(",");
    ID id = ID.get(param[0]);
    if(id == Module.id) {
      return new ActionCreate(null, id, null);
    } else {
      EntityStack stack0 = EntityStack.all.get(id);
      Function function0 = Function.all.get(id);
      if(stack0 == null) {
        if(function0 == null) throw new ElException("Function " + id
            + " not found.");
        stack0 = EntityStack.call;
      }
      if(stack0 == EntityStack.block || stack0 == EntityStack.constant) {
        if(param.length != 2) throw new ElException("CREATE "
            + stack0.name.string + " command requires 2 parameters");
        return new ActionCreate(stack0, ID.get(param[1]), null);
      } else {
        if(param.length != 1) throw new ElException(
            "CREATE command requires single parameter");
        return new ActionCreate(stack0, null, function0);
      }
    }
  }
  
  @Override
  public void execute() throws ElException {
    currentAction = this;
    if(type == Module.id) {
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
      allocate();
    } else if(function != null) {
      if(log) log("CREATE FUNCTION CALL(" + function.getClassName() + ")");
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
