package parser;

import java.util.LinkedList;

public class ActionSwitchToken extends Action {
  private static class Entry {
    private final String token;
    private final Action action;

    public Entry(String token, Action action) {
      this.token = token;
      this.action = action;
    }
  }
  
  private final LinkedList<Entry> entries = new LinkedList<>();
  public Action defaultAction;
  private final int index;

  public ActionSwitchToken(int index) {
    this.index = index;
  }

  void addEntry(String token, Action action) {
    entries.add(new Entry(token, action));
  }

  @Override
  public Action execute() {
    String token = currentScope.variables[index].caption;
    for(Entry entry : entries) {
      if(entry.token.equals(token)) {
        if(log) System.out.println(" SWITCH TO " + token);
        return entry.action;
      }
    }
    return defaultAction;
  }
}
