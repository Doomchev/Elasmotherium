import base.Base;
import base.Module;
import parser.Rules;
import processor.Processor;

public class Test extends Base {
  public static void main(String[] args) {
    test("examples/class2.es");
  }

  static void test(String file) {
    Rules rules = new Rules().load("parsers/standard.parser");
    Module module = Module.read(file, rules);
    Processor processor = Processor.load("");
    //processor.process(module);
    module.print();
  }
}
