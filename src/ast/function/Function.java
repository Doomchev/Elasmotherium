package ast.function;

import ast.ID;
import ast.IDEntity;
import ast.NamedEntity;

public class Function extends NamedEntity {
  public static final ID id = ID.get("function");

  public Function(ID name, int textStart, int textEnd) {
    super(name, textStart, textEnd);
  }
  
  public Function(IDEntity name) {
    super(name);
  }
}
