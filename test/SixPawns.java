
import export.Export;
import base.Module;
import parser.Rules;

public class SixPawns {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("examples/six_pawns/main.es", rules);
    //System.out.println(new NodeProcessor(rules).processModule(module).log());
    
    Export export = new Export(rules).load("java.eec");
    System.out.println("\n" + export.exportEntity(module.main));
  }
}
