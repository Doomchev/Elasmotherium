package parser;

import java.util.LinkedList;
import static parser.ParserBase.log;
import static parser.ParserBase.currentParserScope;

public class ActionSwitchCategory extends ActionSwitch {
  private static class Entry {
    private final Category category;
    private final Action action;

    public Entry(Category category, Action action) {
      this.category = category;
      this.action = action;
    }
  }
  
  private final LinkedList<Entry> entries = new LinkedList<>();
  public Action defaultAction;
  private final int index;

  public ActionSwitchCategory(int index) {
    this.index = index;
  }

  @Override
  public void setStringAction(String token, Action action) {
    actionError("String key is not allowed");
  }

  @Override
  public void setOtherAction(Action action) {
    defaultAction = action;
  }

  @Override
  public void setCategoryAction(Category category, Action action) {
    entries.add(new Entry(category, action));
  }

  @Override
  public Action execute() {
    Category category = currentParserScope.variables[index].category;
    for(Entry entry : entries) {
      if(entry.category == category) {
        if(log) log("SWITCH TO " + category.name);
        return entry.action;
      }
    }
    return defaultAction;
  }

}
