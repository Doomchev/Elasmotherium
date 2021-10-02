package snippets;

import org.junit.Test;

public class Arithmetic extends TestBase {
  @Test
  public void unary() {
    execute("assert(2 + -1 == 1);");
  }
  
  @Test
  public void binary() {
    append("assert(1 + 2 == 3);");
    append("assert(7 - 5 == 2);");
    append("assert(2 * 3 == 6);");
    execute("assert(6 / 3 == 2);");
  }
  
  @Test
  public void brackets() {
    execute("assert((1 + 2) * (4 - 3) / 3 == 1);");
  }
  
  @Test
  public void precedence() {
    execute("assert(4 / 2 + 2 * 3 == 8);");
  }
}
