package ast.function;

import ast.ID;
import ast.NamedEntity;

public class Function extends NamedEntity {
  public static final ID id = ID.get("function");
  
  public Function(ID name) {
    super(name);
  }

  public Function(String name) {
    super(name);
  }
}
