
import export.Export;
import base.Module;
import parser.Rules;
import parser.structure.NodeProcessor;

public class SixPawns {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.eep");
    Module module = Module.read("examples/six_pawns/main.ees", rules);
    module.rootNode.log("");
    System.out.println(new NodeProcessor(rules).processModule(module).log());
    
    //Export export = new Export(rules).load("java.eee");
    //System.out.println("\n" + export.exportNode(module.rootNode));
  }
}
