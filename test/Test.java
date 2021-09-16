import base.Base;
import base.Module;

public class Test extends Base {
  static void test(String name) {
    test(name, true);
  }

  static void test(String name, boolean showCommands) {
    Module.execute("examples", name, showCommands);
  }
}