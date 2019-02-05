package parser;

import parser.structure.Node;

class ActionReturn extends Action {
  private final Node structure;

  public ActionReturn(Node structure) {
    this.structure = structure;
  }
  
  @Override
  public Action execute() {
    if(parserScopes.isEmpty()) actionError("RETURN without function call");
    ActionSub sub = currentParserScope.sub;
    
    if(sub.storingIndex >= 0) {
      Node returnValue = structure.resolve();
      currentParserScope = parserScopes.pop();
      currentParserScope.variables[sub.storingIndex] = returnValue;
      if(log) log("RETURN(" + returnValue.toString() + ") to " + sub.storingIndex);
    } else {
      currentParserScope = parserScopes.pop();
      if(log) log("RETURN");
    }
      
    if(log) System.out.println(currentParserScope.category.toString());
    return sub.nextAction;
  }

  @Override
  public String toString() {
    return structure == null ? "null" : structure.toString();
  }
}
