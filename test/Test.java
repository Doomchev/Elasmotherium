import base.Base;
import base.Module;
import parser.Rules;
import processor.Processor;
import vm.VMBase;

public class Test extends Base {
  public static void main(String[] args) {
    test("examples/class2.es");
  }

  static void test(String file) {
    Rules rules = new Rules().load("parsers/standard.parser");
    Module module = Module.read(file, rules);
    new Processor().load("processors/standard.processor").process(module);
    VMBase.prepare();
    VMBase.exec();
  }
}