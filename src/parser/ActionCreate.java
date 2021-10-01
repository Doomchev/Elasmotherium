package parser;

import base.Module;
import ast.Block;
import ast.ID;
import ast.function.NativeFunction;
import ast.exception.ElException;
import ast.exception.ElException.MethodException;
import ast.exception.NotFound;

public class ActionCreate extends Action {
  private final EntityStack stack;
  private final NativeFunction function;
  private final ID type;

  public ActionCreate(EntityStack stack, ID type, NativeFunction function) {
    this.stack = stack;
    this.type = type;
    this.function = function;
  }
  
  @Override
  public Action create(String params) throws ElException {
    String[] param = params.split(",");
    ID id = ID.get(param[0]);
    if(id == Module.id) {
      return new ActionCreate(null, id, null);
    } else {
      EntityStack stack0;
      NativeFunction function0;
      try {
        function0 = NativeFunction.get(id);
        stack0 = EntityStack.function;
      } catch(NotFound ex) {
        function0 = null;
        stack0 = EntityStack.all.get(id);
        if(stack0 == null)
          throw new ElException.ActionException(this, "function " + id
              , "not found");
      }
      if(stack0 == EntityStack.block || stack0 == EntityStack.constant) {
        if(param.length != 2)
          throw new MethodException(this, "create", "CREATE "
              + stack0.name.string + " command requires 2 parameters");
        return new ActionCreate(stack0, ID.get(param[1]), null);
      } else {
        if(param.length != 1)
          throw new MethodException(this, "create"
              , "CREATE command requires single parameter");
        return new ActionCreate(stack0, null, function0);
      }
    }
  }
  
  @Override
  public void execute() throws ElException {
    currentAction = this;
    if(type == Module.id) {
      String name = EntityStack.id.pop().value.string;
      Module.add(name);
      if(log) log("CREATE MODULE " + name);
    } else if(stack.isStringBased()) {
      String string = currentSymbolReader.getString();
      if(log) log("CREATE " + (type == null ? "" : type + " ")
          + stack.name.string + "(" + string + ")");
      stack.push(stack.create(string, type, currentSymbolReader
          .getEntityStart(), currentSymbolReader.getTextPos()));
      currentSymbolReader.clear();
    } else if(type != null) {
      if(log) log("CREATE BLOCK " + type.string);
      stack.push(Block.create(type));
    } else if(function != null) {
      if(log) log("CREATE FUNCTION(" + function + ")");
      stack.push(function);
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
