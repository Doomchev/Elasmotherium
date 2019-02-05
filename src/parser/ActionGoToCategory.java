package parser;

public class ActionGoToCategory extends Action {
  private final Category category;
  
  public ActionGoToCategory(Category category) {
    this.category = category;
  }
  
  @Override
  public Action execute() {
    if(log) log(toString() + "\n" + category.name);
    currentParserScope.category = category;
    return category.action;
  }

  @Override
  public String toString() {
    return " GOTO(" + category.name + ')';
  }
}
