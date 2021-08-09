package ast;

import java.util.HashMap;

public class ID {
  public static HashMap<String, ID> all = new HashMap<>();  
  public String string;

  private ID(String string) {
    this.string = string;
  }
  
  public static ID get(String string) {
    string = string.trim();
    ID id = all.get(string);
    if(id == null) {
      id = new ID(string);
      all.put(string, id);
    }
    return id;
  }
  
  public static class Stack {
    public static Stack instance = new Stack();
    
    public final java.util.Stack<ID> stack = new java.util.Stack<>();

    private Stack() {
    }
    
    public static ID pop() {
      if(instance.stack.isEmpty()) return null;
      return instance.stack.pop();
    }
  }

  @Override
  public String toString() {
    return string;
  }
}
