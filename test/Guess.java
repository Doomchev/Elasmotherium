
import base.Base;
import export.Export;
import base.Module;
import base.Processor;
import parser.Rules;

public class Guess extends Base {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("examples/guess.es", rules);
    Processor.process();
    //new Export(rules).load(module, JAVA).log();
  }
}
