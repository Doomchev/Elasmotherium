
import export.Export;
import base.Module;
import parser.Rules;
import parser.structure.NodeProcessor;
import parser.structure.Scope;

public class Test {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.eep");
    Module module = Module.read("src/examples/test/test.ees", rules);
    Scope scope = new NodeProcessor(rules).processModule(module);
    module.rootNode.log("");
    System.out.println(scope.log());
    
    Export export = new Export(rules).load("java.eee");
    System.out.println("\n" + export.exportNode(module.rootNode));
  }
}
