package parser;

import ast.ID;
import java.util.LinkedList;
import static parser.ParserBase.log;
import ast.EntityStack;

public class ActionSwitchID extends ActionSwitch {
  private static class Entry {
    private final ID id;
    private final Action action;

    public Entry(ID id, Action action) {
      this.id = id;
      this.action = action;
    }
  }
  
  private final LinkedList<Entry> entries = new LinkedList<>();
  public Action defaultAction;

  @Override
  public void setStringAction(String token, Action action) {
    entries.add(new Entry(ID.get(token), action));
  }

  @Override
  public void setOtherAction(Action action) {
    defaultAction = action;
  }

  @Override
  public Action execute() {
    ID id = EntityStack.id.peek();
    if(id == null) actionError("Trying to switch id with no id in stack");
    for(Entry entry : entries) {
      if(entry.id == id) {
        EntityStack.id.pop();
        if(log) log("SWITCH TO " + id.string);
        return entry.action;
      }
    }
    return defaultAction;
  }

}
