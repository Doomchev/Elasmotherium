import base.Module;

public class Test {
  static void test(String name) {
    test(name, true);
  }

  static void test(String name, boolean showCommands) {
    Module.execute("examples", name, showCommands);
  }
}