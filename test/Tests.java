
import parser.Export;
import parser.Module;
import parser.Rules;

public class Tests {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.xep");
    Export export = new Export(rules).load("javascript.xee");
    Module module = Module.read("examples/tests.xes", rules);
    module.rootNode.log("");
    System.out.println("\n" + export.exportNode(module.rootNode));
  }
}
