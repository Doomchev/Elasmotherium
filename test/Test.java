
import export.Export;
import base.Module;
import parser.Rules;
import parser.structure.NodeProcessor;

public class Test {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.eep");
    Module module = Module.read("examples/test.ees", rules);
    System.out.println(new NodeProcessor(rules).processModule(module).log());
    module.rootNode.log("");
    
    Export export = new Export(rules).load("java.eee");
    System.out.println("\n" + export.exportNode(module.rootNode));
  }
}
