import base.Base;
import base.Module;
import parser.Rules;

public class Test extends Base {
  public static void main(String[] args) {
    test("examples/class2.es");
  }

  static void test(String file) {
    Rules rules = new Rules().load("parsers/standard.parser");
    Module module = Module.read(file, rules);
    module.print();
  }
}
