
import base.Base;
import export.Export;
import base.Module;
import base.Processor;
import parser.Rules;

public class Test extends Base {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("src/examples/test/test.es", rules);
    Processor.process();
    new Export(rules).load(module, JAVA).log();
  }
}
