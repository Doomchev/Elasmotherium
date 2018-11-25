package parser;

class ActionReturn extends Action {
  private final Node structure;

  public ActionReturn(Node structure) {
    this.structure = structure;
  }
  
  @Override
  public Action execute() {
    if(scopes.isEmpty()) parsingError("RETURN without function call");
    ActionSub sub = currentScope.sub;
    
    if(sub.storingIndex >= 0) {
      Node returnValue = structure.resolve();
      currentScope = scopes.pop();
      currentScope.variables[sub.storingIndex] = returnValue;
      if(log) System.out.println(" RETURN(" + returnValue.toString() + ")");
    } else {
      currentScope = scopes.pop();
      if(log) System.out.println(" RETURN");
    }
      
    System.out.println(currentScope.category.toString());
    return sub.nextAction;
  }

  @Override
  public String toString() {
    return structure == null ? "null" : structure.toString();
  }
}
