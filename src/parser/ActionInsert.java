package parser;

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
    if(currentScope.variables[index] == null) parsingError("Null node insertion");
    currentScope.variables[index].children.add(struc.resolve());
    if(log) System.out.println(" INSERT(" + index + ", "
        + struc.resolve().toString() + ")");
    return nextAction;
  }

  @Override
  public String toString() {
    return index + "," + struc.toString();
  }
}
