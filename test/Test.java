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
    test(file, true);
  }

  static void test(String file, boolean showCommands) {
    Module module = Module.read(file
        , new Rules().load("parsers/standard.parser"));
    new Processor().load("processors/standard.processor").process(module);
    VMBase.execute(showCommands, module);
  }
}