import base.Base;
import base.Module;
import parser.Rules;
import processor.Processor;

public class Test extends Base {
  static void test(String name) {
    test(name, true);
  }

  static void test(String name, boolean showCommands) {
    Module module = Module.read("examples", name
        , new Rules().load("parsers/standard.parser"));
    new Processor().load("processors/standard.processor").process(module);
    module.execute(showCommands);
  }
}