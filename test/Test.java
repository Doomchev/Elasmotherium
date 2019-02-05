
import export.Export;
import base.Module;
import parser.Rules;

public class Test {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.eep");
    Export export = new Export(rules).load("javascript.eee");
    Module module = Module.read("examples/test.ees", rules);
    module.rootNode.log("");
    //System.out.println("\n" + export.exportNode(module.rootNode));
  }
}
