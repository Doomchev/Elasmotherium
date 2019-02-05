package parser;

import parser.structure.Node;

class ActionInsert extends Action {
  private final int index;
  private final Node struc;

  public ActionInsert(int index, Node struc) {
    this.index = index;
    this.struc = struc;
    //System.out.println(struc.toString());
  }
  
  @Override
  public Action execute() {
    if(currentParserScope.variables[index] == null) actionError("Null node insertion");
    currentParserScope.variables[index].children.add(struc.resolve());
    if(log) log("INSERT(" + index + ", " + struc.resolve().toString() + ")");
    return nextAction;
  }

  @Override
  public String toString() {
    return index + "," + struc.toString();
  }
}
