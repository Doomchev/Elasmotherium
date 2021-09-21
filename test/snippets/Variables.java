package snippets;

import org.junit.Test;

public class Variables extends TestBase {
  @Test
  public void definition() {
    execute("Int a;");
  }
  
  @Test
  public void assigning() {
    execute("Int a = 1;");
    execute("assert(a == 1);");
  }
  
  @Test
  public void operations() {
    append("Int a = 2;");
    append("Int b = a * 5;");
    execute("assert(b == 10);");
  }
}