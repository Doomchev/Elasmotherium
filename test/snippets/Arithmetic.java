package snippets;

import base.Module;
import org.junit.Test;

public class Arithmetic {
  StringBuffer text = new StringBuffer();
  
  protected void append(String line) {
    text.append(line).append("\n");
  }
  
  protected void execute(String line) {
    append(line);
    execute();
  }
  
  protected void execute() {
    Module.execute(text);
  }
  
  @Test
  public void operations() {
    execute("assert((1 + 2) * (3 - 4) / 3 == -1);");
  }
}
