package base;

import ast.exception.ElException;
import ast.ClassEntity;
import ast.ID;
import ast.NamedEntity;
import ast.exception.NotFound;
import static base.Debug.println;
import java.util.LinkedList;

public class Scopes extends Debug {
  private static final NamedEntity[] scope = new NamedEntity[1024];
  private static final LinkedList<Integer> scopeEnd = new LinkedList<>();
  private static int lastScopeEntry = -1;
      
  public static void allocateScope() {
    scopeEnd.add(lastScopeEntry);
  }
  
  public void deallocateScope() {
    lastScopeEntry = scopeEnd.getLast();
    scopeEnd.removeLast();
  }
  
  public void addToScope(NamedEntity entity) {
    lastScopeEntry++;
    scope[lastScopeEntry] = entity;
  }
  
  public NamedEntity getFunctionFromScope(ID name, int parametersQuantity)
      throws NotFound {
    for(int i = lastScopeEntry; i >= 0; i--)
      if(scope[i].isFunction(name, parametersQuantity)) return scope[i];
    throw new NotFound("Function " + name.string, parametersQuantity);
  }
  
  public NamedEntity getVariableFromScope(ID name, boolean isThis)
      throws NotFound {
    for(int i = lastScopeEntry; i >= 0; i--)
      if(scope[i].isValue(name, isThis)) return scope[i];
    throw new NotFound("Variable " + name.string);
  }
  
  public ClassEntity getClassFromScope(ID name) throws NotFound {
    for(int i = lastScopeEntry; i >= 0; i--) {
      NamedEntity entity = scope[i];
      if(entity instanceof ClassEntity && entity.isValue(name, false))
        return (ClassEntity) entity;
    }
    throw new NotFound("Class " + name);
  }
  
  public void printScope() {
    StringBuilder string = new StringBuilder();
    for(int i = 0; i <= lastScopeEntry; i++) {
      if(i > 0) string.append(", ");
      string.append(scope[i].getName());
    }
    println(string.toString());
  }
}
