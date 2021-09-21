package snippets;

import base.Module;

public class TestBase {
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
}