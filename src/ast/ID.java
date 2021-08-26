package ast;

import java.util.HashMap;

public class ID {
  public static final HashMap<String, ID> all = new HashMap<>();  
  
  public final String string;

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

  @Override
  public String toString() {
    return string;
  }
}
