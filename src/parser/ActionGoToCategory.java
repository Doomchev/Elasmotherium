package parser;

public class ActionGoToCategory extends Action {
  private final Category category;
  
  public ActionGoToCategory(Category category) {
    this.category = category;
  }
  
  @Override
  public Action execute() {
    if(log) {
      System.out.println(toString());
      System.out.println(category.name);
    }
    currentScope.category = category;
    return category.action;
  }

  @Override
  public String toString() {
    return " GOTO(" + category.name + ')';
  }
}
