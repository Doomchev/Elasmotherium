
import base.Base;
import base.Module;
import base.Processor;
import parser.Rules;
import vm.VMBase;

public class Addition extends Base {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module.read("examples/addition.es", rules);
    Processor.process();
    VMBase.prepare(true);
  }
}
