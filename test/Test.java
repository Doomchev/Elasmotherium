
import export.Export;
import base.Module;
import base.Processor;
import parser.Rules;

public class Test {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("examples/test.es", rules);
    Processor.process(module);
    new Export(rules).load("java.eec").log();
  }
}
