package ast;

public class ID extends Entity {
  public String string;

  private ID(String string) {
    this.string = string;
  }

  @Override
  public ID getID() {
    return idID;
  }
  
  public static ID get(String string) {
    ID id = allIDs.get(string);
    if(id == null) {
      id = new ID(string);
      allIDs.put(string, id);
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
