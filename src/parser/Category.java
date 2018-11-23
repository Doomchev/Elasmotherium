package parser;

public class Category {
  public final String name;
  public Action action;
  public int priority = 0;


  public Category(String name) {
    this.name = name;
    this.action = null;
  }

  public Category(String name, Action action) {
    this.name = name;
    this.action = action;
  }

  @Override
  public String toString() {
    return name;
  }
}
