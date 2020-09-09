import base.Base;
import export.Export;
import base.Module;
import base.Processor;
import parser.Rules;
import vm.VMBase;

public class Factorial extends Base {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("examples/factorial.es", rules);
    Processor.process();
    VMBase.prepare();
    //new Export(rules).load(module, JAVA).log();
  }
}
