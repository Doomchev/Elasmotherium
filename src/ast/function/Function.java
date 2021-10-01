package ast.function;

import ast.Entity;
import ast.ID;
import ast.IDEntity;
import ast.NamedEntity;
import java.util.LinkedList;

public class Function extends NamedEntity {
  public static final ID id = ID.get("function");

  public Function(ID name, int textStart, int textEnd) {
    super(name, textStart, textEnd);
  }
  
  public Function(IDEntity name) {
    super(name);
  }

  @Override
  public String toString(LinkedList<Entity> parameters) {
    return name + "(" + listToString(parameters) + ")";
  }
}
