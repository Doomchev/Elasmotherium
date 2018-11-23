package parser;

public class ActionCreate extends Action {
  private final int index;
  private final Node struc;

  public ActionCreate(int index, Node struc) {
    this.index = index;
    this.struc = struc;
    //System.out.println(struc.toString());
  }
  
  @Override
  public Action execute() {
    currentScope.variables[index] = struc.resolve();
    if(log) System.out.println(" CREATE(" + index + ","
        + struc.resolve().toString() + ")");
    return nextAction;
  }

  @Override
  public String toString() {
    return index + "," + struc.toString();
  }
}
